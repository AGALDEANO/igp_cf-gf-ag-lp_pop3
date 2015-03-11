package base.client;

import base.email.Email;
import base.email.EmailUtil;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;
import util.Action;
import util.CurrentState;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class ClientThrowable extends Thread implements Client {
    private static Logger logger = Logger.getLogger(ClientThrowable.class.getName());
    private CurrentState currentState = null;
    private int unreadMessage = 0;
    private Action waitingTask = null;
    private String[] waitingTaskArgs = null;
    private Boolean quit = Boolean.FALSE;
    private String sucessMessage = null;
    private String errorMessage = null;
    private Email email = null;
    private String username;
    private Boolean autosave = Boolean.TRUE;

    @Override
    public synchronized String getSucessMessage() {
        String message = sucessMessage;
        sucessMessage = null;
        return message;
    }

    private synchronized void setSucessMessage(String sucessMessage) {
        this.sucessMessage = sucessMessage;
    }

    @Override
    public synchronized Email getMessage() {
        if (email == null) return null;
        Email message = new Email(email);
        email = null;
        return message;
    }

    private synchronized void setMessage(Email email) {
        this.email = email;
    }

    @Override
    public synchronized String getErrorMessage() {
        String message = errorMessage;
        errorMessage = null;
        return message;
    }

    private synchronized void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public synchronized String[] getWaitingTaskArgs() {
        return waitingTaskArgs;
    }

    private synchronized void setWaitingTaskArgs(String[] waitingTaskArgs) {
        this.waitingTaskArgs = waitingTaskArgs;
    }

    @Override
    public synchronized CurrentState getCurrentState() {
        return currentState;
    }

    private synchronized void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    @Override
    public synchronized int getUnreadMessage() {
        return unreadMessage;
    }

    private synchronized void setUnreadMessage(int unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    private synchronized Action getWaitingTask() {
        return waitingTask;
    }

    private synchronized void setWaitingTask(Action waitingTask) {
        this.waitingTask = waitingTask;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p/>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        Action todo;
        String[] todoArgs;
        String message;
        while (!(quit && getWaitingTask() == null && getWaitingTaskArgs() == null)) {
            todo = getWaitingTask();
            todoArgs = getWaitingTaskArgs();
            if (todo != null && todoArgs != null) {
                try {
                    message = todo.execute(currentState, todoArgs);
                    if (Action.APOP.equals(todo)) {
                        username = todoArgs[0];
                    }
                    if (Action.QUIT.equals(todo)) {
                        currentState = null;
                    }
                    if (Action.RETR.equals(todo)) {
                        Email received = new Email(message);
                        setMessage(received);
                        try {
                            if (autosave) EmailUtil.saveEmail(received, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.debug(message);
                    setCurrentState(CurrentState.changeTo(getCurrentState(), todo.getIfSucceed()));
                    setSucessMessage(message);
                } catch (UnallowedActionException e) {
                    message = e.getMessage();
                    logger.error(message);
                    setErrorMessage(message);
                } catch (ErrorResponseServerException e) {
                    setCurrentState(CurrentState.changeTo(getCurrentState(), todo.getIfFailed()));
                    message = e.getMessage();
                    logger.error(message);
                    setErrorMessage(message);
                } catch (UnrespondingServerException e) {
                    message = e.getMessage();
                    logger.error(message);
                    setErrorMessage(message);
                } finally {
                    setWaitingTask(null);
                    setWaitingTaskArgs(null);
                }
            }
        }
    }

    public void openConnexion(String hostname, int port) {
        setWaitingTask(Action.CONNEXION);
        String[] args = {hostname, Integer.toString(port)};
        setWaitingTaskArgs(args);
    }

    public void closeConnexion() {
        setWaitingTask(Action.QUIT);
        String[] args = {};
        setWaitingTaskArgs(args);
    }

    public void exit() {
        quit = Boolean.TRUE;
    }

    public void signIn(String username, String password) {
        setWaitingTask(Action.APOP);
        String[] args = {username, password};
        setWaitingTaskArgs(args);
    }

    public void signIn(String username) {
        setWaitingTask(Action.APOP);
        String[] args = {username, ""};
        setWaitingTaskArgs(args);
    }

    public void getMessageList() {
        setWaitingTask(Action.LIST);
        String[] args = {};
        setWaitingTaskArgs(args);
    }

    @Override
    public ArrayList<Email> getSavedMessages() {
        if (!CurrentState.TRANSACTION.equals(currentState)) {
            try {
                throw new UnallowedActionException();
            } catch (UnallowedActionException e) {
                String message = e.toString();
                logger.error(message);
                setErrorMessage(message);
                return null;
            }
        }
        return getSavedMessages(username);
    }

    @Override
    public ArrayList<Email> getSavedMessages(String username) {
        try {
            return EmailUtil.getEmails(username);
        } catch (IOException e) {
            String message = e.toString();
            logger.error(message);
            setErrorMessage(message);
            return new ArrayList<>();
        }
    }

    public void getMessageDetails(int i) {
        setWaitingTask(Action.LIST);
        String[] args = {Integer.toString(i)};
        setWaitingTaskArgs(args);
    }

    public void enterLogin(String username) {
        setWaitingTask(Action.USER);
        String[] args = {username};
        setWaitingTaskArgs(args);
    }

    public void enterPassword(String password) {
        setWaitingTask(Action.PASS);
        String[] args = {password};
        setWaitingTaskArgs(args);
    }

    public void getMessage(int num) {
        setWaitingTask(Action.RETR);
        String[] args = {Integer.toString(num)};
        setWaitingTaskArgs(args);
    }

    public void getStat() {
        setWaitingTask(Action.STAT);
        String[] args = {};
        setWaitingTaskArgs(args);
    }
}
