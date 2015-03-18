package base.client;

import base.email.Email;
import base.email.EmailUtil;
import exception.BadFormatResponseServerException;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;
import util.Action;
import util.CurrentState;
import util.ServerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Client {
	private static Logger logger = Logger.getLogger(Client.class.getName());
	private CurrentState currentState = null;
    private int unreadMessage = 0;
    private Action waitingTask = null;
    private String[] waitingTaskArgs = null;
    private Boolean quit = Boolean.FALSE;
	private Response response = new Response();
	private String username;
	private String timestamp = "";

	public String getTimestamp() {
		return timestamp;
	}

	private void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSucessMessage() {
		String message = response.sucessMessage;
		response.sucessMessage = null;
		return message;
    }

	private void setSucessMessage(String sucessMessage) {
		response.sucessMessage = sucessMessage;
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public Email getMessage() {
		if (response.email == null)
			return null;
		Email message = new Email(response.email);
		response.email = null;
		return message;
    }

	private void setMessage(Email email) {
		this.response.email = email;
	}

	public String getErrorMessage() {
		String message = response.errorMessage;
		response.errorMessage = null;
		return message;
    }

	private void setErrorMessage(String errorMessage) {
		this.response.errorMessage = errorMessage;
	}

	public String[] getWaitingTaskArgs() {
		return waitingTaskArgs == null ?
				null :
				Arrays.copyOf(waitingTaskArgs, waitingTaskArgs.length);
	}

	private void setWaitingTaskArgs(String[] waitingTaskArgs) {
		this.waitingTaskArgs = waitingTaskArgs;
    }

	public CurrentState getCurrentState() {
		return currentState;
    }

	private void setCurrentState(CurrentState currentState) {
		this.currentState = currentState;
    }

	public int getUnreadMessage() {
		return unreadMessage;
    }

	private void setUnreadMessage(int unreadMessage) {
		this.unreadMessage = unreadMessage;
    }

	private Action getWaitingTask() {
		return waitingTask;
    }

	private void setWaitingTask(Action waitingTask) {
		this.waitingTask = waitingTask;
    }

	public void run() {
        Action todo;
        String[] todoArgs;
        String message;
		if (!(quit && getWaitingTask() == null
				&& getWaitingTaskArgs() == null)) {
			todo = getWaitingTask();
            todoArgs = getWaitingTaskArgs();
            if (todo != null && todoArgs != null) {
                try {
                    message = todo.execute(currentState, todoArgs);
					if (Action.CONNEXION.equals(todo) && Config.getApop()) {
						try {
							setTimestamp(ServerUtil.getInstance()
									.computeTimeStamp(message));
						} catch (BadFormatResponseServerException e) {
							message = e.getMessage();
							logger.error(message);
							setTimestamp("");
						}
					}
					if (Action.APOP.equals(todo)) {
						setUsername(todoArgs[0]);
					}
                    if (Action.QUIT.equals(todo)) {
                        currentState = null;
                    }
                    if (Action.RETR.equals(todo)) {
                        Email received = new Email(message);
                        setMessage(received);
                        try {
							if (Config.getAutosave())
								EmailUtil.saveEmail(received, username);
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
		String[] args = { hostname, Integer.toString(port) };
		setWaitingTaskArgs(args);
		run();
	}

    public void closeConnexion() {
        setWaitingTask(Action.QUIT);
        String[] args = {};
        setWaitingTaskArgs(args);
		run();
	}

    public void exit() {
        quit = Boolean.TRUE;
		run();
	}

    public void signIn(String username, String password) {
        setWaitingTask(Action.APOP);
        String[] args = {username, password};
        setWaitingTaskArgs(args);
		run();
	}

    public void signIn(String username) {
        setWaitingTask(Action.APOP);
        String[] args = {username, ""};
        setWaitingTaskArgs(args);
		run();
	}

    public void getMessageList() {
        setWaitingTask(Action.LIST);
        String[] args = {};
        setWaitingTaskArgs(args);
		run();
	}

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
		run();
	}

    public void enterLogin(String username) {
        setWaitingTask(Action.USER);
        String[] args = {username};
        setWaitingTaskArgs(args);
		run();
	}

    public void enterPassword(String password) {
        setWaitingTask(Action.PASS);
        String[] args = {password};
        setWaitingTaskArgs(args);
		run();
	}

    public void getMessage(int num) {
        setWaitingTask(Action.RETR);
        String[] args = {Integer.toString(num)};
        setWaitingTaskArgs(args);
		run();
	}

    public void getStat() {
        setWaitingTask(Action.STAT);
        String[] args = {};
        setWaitingTaskArgs(args);
		run();
	}
}
