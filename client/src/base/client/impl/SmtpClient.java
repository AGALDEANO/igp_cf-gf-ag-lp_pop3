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
import java.text.SimpleDateFormat;
import java.util.*;

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

    private String getUsername() {
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

    private String[] getWaitingTaskArgs() {
        return waitingTaskArgs == null ?
                null :
                Arrays.copyOf(waitingTaskArgs, waitingTaskArgs.length);
    }

    private void setWaitingTaskArgs(String[] waitingTaskArgs) {
        this.waitingTaskArgs = waitingTaskArgs;
    }

    private SmtpState getSmtpState() {
        return smtpState;
    }

    private void setSmtpState(SmtpState smtpState) {
        this.smtpState = smtpState;
    }

    private int getUnreadMessage() {
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

    private void run() throws ErrorResponseServerException, UnrespondingServerException {
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
                        Email sent = new Email(waitingTaskArgs[0]);
                        for (EmailHeader header : sent.getHeaders()) {
                            if (Header.FROM.equals(header.getHeader())) username = header.getValue();
                        }
                        try {
                            if (Config.getAutosave())
                                EmailUtil.saveSentEmail(sent, username);
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
                    throw e;
                } catch (UnrespondingServerException e) {
                    message = e.getMessage();
                    logger.error(message);
                    setErrorMessage(message);
                    throw e;
                } finally {
                    setWaitingTask(null);
                    setWaitingTaskArgs(null);
                }
            }
        }
    }


    private void sendEmail(String body, EmailHeader... headers) throws ErrorResponseServerException, UnrespondingServerException {
        body = body.replaceAll("\r\n", "\n")
                .replaceAll("\n", "\r\n")
                .replaceAll("\r\n.\r\n", "\r\n.\n");
        String from = "";
        ArrayList<String> invalidTos = new ArrayList<>();
        LinkedList<String> tos = new LinkedList<>();
        String value;
        HashMap<Header, EmailHeader> emailHeaders = new HashMap<>();
        for (EmailHeader header : headers) {
            Header h = header.getHeader();
            if (emailHeaders.containsKey(h)) emailHeaders.put(h,
                    new EmailHeader(h, emailHeaders.get(h).getValue() + ", " + header.getValue()));
            else emailHeaders.put(h, header);
            if (Header.FROM.equals(h)) {
                if (from != "") throw new RuntimeException("Multiple FROM");
                value = header.getValue();
                if (EmailUtil.validEmailAddress(value)) from = value;
            }
            if (Header.TO.equals(h) || Header.CC.equals(h) || Header.BCC.equals(h)) {
                tos.addLast(header.getValue());
            }
        }

        if (from == null) throw new RuntimeException("No from set");

        ehlo(from);
        mailfrom(from);
        int nbTos = 0;
        String last;
        while (tos.size() > 1) {
            last = tos.getLast();
            if (EmailUtil.validEmailAddress(last)) {
                rcpt(last);
                if (response.getSucessMessage() != null && response.getSucessMessage().contains("553"))
                    invalidTos.add(last);
                else nbTos++;
            }
            tos.removeLast();
        }

        if (tos.size() == 1) {
            last = tos.getLast();
            if (EmailUtil.validEmailAddress(last)) {
                lastRcpt(last);
                if (response.getSucessMessage() != null && response.getSucessMessage().contains("553"))
                    invalidTos.add(last);
                else nbTos++;
            }
            tos.removeLast();
        }

        if (nbTos == 0) throw new RuntimeException("No to set");

        data();

        emailHeaders.put(Header.DATE, new EmailHeader(Header.DATE, new SimpleDateFormat("MM/dd/yyyy - hh:mm:ss a").format(new Date())));
        emailHeaders.put(Header.MESSAGE_ID, new EmailHeader(Header.MESSAGE_ID, Long.toHexString(new Date().getTime())));
        sendEmailAction(body, emailHeaders.values().toArray(headers));
        closeConnexion();
        exit();
        int s = invalidTos.size();
        if (s != 0) {
            StringBuilder stringBuilder = new StringBuilder(
                    "Le message n'a pas pu être distribué au" + (s == 1 ? "" : "x")
                            + " destinataire" + (s == 1 ? "" : "s")
                            + " suivant" + (s == 1 ? "" : "s") + " :\n");
            for (String invalidTo : invalidTos) {
                stringBuilder.append("- ");
                stringBuilder.append(invalidTo);
                stringBuilder.append('\n');
            }
            throw new ErrorResponseServerException(stringBuilder.toString());
        }
    }

    public void sendEmail(String body, ArrayList<EmailHeader> headers) throws ErrorResponseServerException, UnrespondingServerException {
        ArrayList<String>[] tos = new ArrayList[3];
        tos[0] = new ArrayList<>();
        tos[1] = new ArrayList<>();
        tos[2] = new ArrayList<>();
        EmailHeader header;
        for (int i = 0; i < headers.size(); i++) {
            header = headers.get(i);
            if (Header.TO.equals(header.getHeader())) {
                tos[0].addAll(processHeaderValue(header.getValue()));
                headers.remove(i);
                i--;
            } else if (Header.CC.equals(header.getHeader())) {
                tos[1].addAll(processHeaderValue(header.getValue()));
                headers.remove(i);
                i--;
            } else if (Header.BCC.equals(header.getHeader())) {
                tos[2].addAll(processHeaderValue(header.getValue()));
                headers.remove(i);
                i--;
            }
        }
        for (String to : tos[0]) {
            headers.add(new EmailHeader(Header.TO, to));
        }
        for (String cc : tos[1]) {
            headers.add(new EmailHeader(Header.CC, cc));
        }
        for (String bcc : tos[2]) {
            headers.add(new EmailHeader(Header.BCC, bcc));
        }
        EmailHeader[] a = new EmailHeader[headers.size()];
        sendEmail(body, headers.toArray(a));
    }

    private ArrayList<String> processHeaderValue(String value) {
        value = value.replaceAll(" ", "");
        String[] split = value.split(";");
        ArrayList<String> strings = new ArrayList<>();
        for (String string : split) {
            if (!"".equals(string)) strings.add(string);
        }
        return strings;
    }


    private void sendEmailAction(String body, EmailHeader... headers) throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.SENDEMAIL);
        String[] args = {EmailUtil.emailBodyHeadersToString(body, headers)};
        setWaitingTaskArgs(args);
        run();
    }


    public void openConnexion(String hostname, int port) throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.CONNEXION);
        String[] args = {hostname, Integer.toString(port)};
        setWaitingTaskArgs(args);
        run();
    }

    private void closeConnexion() throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.QUIT);
        String[] args = {};
        setWaitingTaskArgs(args);
        run();
    }

    private void exit() throws ErrorResponseServerException, UnrespondingServerException {
        quit = Boolean.TRUE;
        run();
    }

    private void ehlo(String username) throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.EHLO);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void mailfrom(String username) throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.MAILFROM);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void rcpt(String username) throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.RCPT);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void lastRcpt(String username) throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.LASTRCPT);
        String[] args = {username};
        setWaitingTaskArgs(args);
        run();
    }

    private void data() throws ErrorResponseServerException, UnrespondingServerException {
        setWaitingTask(SmtpAction.DATA);
        String[] args = {};
        setWaitingTaskArgs(args);
        run();
    }

    private ArrayList<Email> getSavedMessages(String username) {
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
