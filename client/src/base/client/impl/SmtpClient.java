package base.client.impl;

import base.client.Client;
import base.client.Config;
import base.email.Email;
import base.email.EmailHeader;
import base.email.EmailUtil;
import base.email.Header;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;
import util.smtp.SmtpAction;
import util.smtp.SmtpState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Alexandre
 *         08/04/2015
 */
public class SmtpClient extends Client {
    private static Logger logger = Logger.getLogger(Pop3Client.class.getName());
    private SmtpState smtpState = null;
    private int unreadMessage = 0;
    private SmtpAction waitingTask = null;

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

    public SmtpState getSmtpState() {
        return smtpState;
    }

    private void setSmtpState(SmtpState smtpState) {
        this.smtpState = smtpState;
    }

    public int getUnreadMessage() {
        return unreadMessage;
    }

    private void setUnreadMessage(int unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    private SmtpAction getWaitingTask() {
        return waitingTask;
    }

    private void setWaitingTask(SmtpAction waitingTask) {
        this.waitingTask = waitingTask;
    }

    private void run() {
        SmtpAction todo;
        String[] todoArgs;
        String message;
        if (!(quit && getWaitingTask() == null
                && getWaitingTaskArgs() == null)) {
            todo = getWaitingTask();
            todoArgs = getWaitingTaskArgs();
            if (todo != null && todoArgs != null) {
                try {
                    message = todo.execute(smtpState, todoArgs);
                    if (SmtpAction.QUIT.equals(todo)) {
                        smtpState = null;
                    }
                    if (SmtpAction.SENDEMAIL.equals(todo)) {
                        Email received = new Email(waitingTaskArgs[0]);
                        try {
                            if (Config.getAutosave())
                                EmailUtil.saveSentEmail(received, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.debug(message);
                    setSmtpState(SmtpState
                            .changeTo(getSmtpState(), todo.getIfSucceed()));
                    setSucessMessage(message);
                } catch (UnallowedActionException e) {
                    message = e.getMessage();
                    logger.error(message);
                    setErrorMessage(message);
                } catch (ErrorResponseServerException e) {
                    setSmtpState(SmtpState
                            .changeTo(getSmtpState(), todo.getIfFailed()));
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


    public void sendEmail(String body, EmailHeader... headers) {
        body = body.replaceAll("\r\n", "\n")
                .replaceAll("\n", "\r\n")
                .replaceAll("\r\n.\r\n", "\r\n.\n");
        String from = "";
        boolean setTo = false;
        LinkedList<String> tos = new LinkedList<>();
        String value;
        for (EmailHeader header : headers) {
            if (Header.FROM.equals(header.getHeader())) {
                if (from != "") throw new RuntimeException("Multiple FROM");
                value = header.getValue();
                if (EmailUtil.validEmailAddress(value)) from = value;
            }
            if (Header.TO.equals(header.getHeader()) || Header.CC.equals(header.getHeader()) || Header.CCI.equals(header.getHeader())) {
                tos.addLast(header.getValue());
            }
        }

        if (from == null) throw new RuntimeException("No from set");

        ehlo(from);
        mailfrom(from);
        String last;
        while (tos.size() > 1) {
            last = tos.getLast();
            if (EmailUtil.validEmailAddress(last)) {
                setTo = true;
                rcpt(last);
            }
            tos.removeLast();
        }

        if (tos.size() == 1) {
            last = tos.getLast();
            if (EmailUtil.validEmailAddress(last)) {
                setTo = true;
                lastRcpt(last);
            }
            tos.removeLast();
        }

        if (setTo == false) throw new RuntimeException("No to set");

        data();

        sendEmailAction(body, headers);
        closeConnexion();
        exit();
    }


    private void sendEmailAction(String body, EmailHeader... headers) {
        setWaitingTask(SmtpAction.SENDEMAIL);
        String[] args = {EmailUtil.emailBodyHeadersToString(body, headers)};
        setWaitingTaskArgs(args);
        run();
    }


    public void openConnexion(String hostname, int port) {
        setWaitingTask(SmtpAction.CONNEXION);
        String[] args = {hostname, Integer.toString(port)};
        setWaitingTaskArgs(args);
        run();
    }

    private void closeConnexion() {
        setWaitingTask(SmtpAction.QUIT);
        String[] args = {};
        setWaitingTaskArgs(args);
        run();
    }

    private void exit() {
        quit = Boolean.TRUE;
        run();
    }

    private void ehlo(String username) {
        setWaitingTask(SmtpAction.EHLO);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void mailfrom(String username) {
        setWaitingTask(SmtpAction.MAILFROM);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void rcpt(String username) {
        setWaitingTask(SmtpAction.RCPT);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void lastRcpt(String username) {
        setWaitingTask(SmtpAction.LASTRCPT);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void data() {
        setWaitingTask(SmtpAction.DATA);
        String[] args = {};
        setWaitingTaskArgs(args);
        run();
    }

    public ArrayList<Email> getSavedMessages(String username) {
        try {
            return EmailUtil.getSentEmails(username);
        } catch (IOException e) {
            String message = e.toString();
            logger.error(message);
            setErrorMessage(message);
            return new ArrayList<>();
        }
    }
}
