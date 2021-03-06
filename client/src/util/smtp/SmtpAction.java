package util.smtp;

import base.Server;
import exception.BadFormatResponseServerException;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;
import util.ServerUtil;
import util.pop3.Pop3Util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by alexandreg on 04/03/2015.
 */
public enum SmtpAction {
    CONNEXION("CONNEXION", SmtpState.START, null),
    EHLO("EHLO", SmtpState.STARTEMAIL, null, SmtpState.START),
    MAILFROM("MAILFROM", SmtpState.FROMSET, SmtpState.STARTEMAIL, SmtpState.STARTEMAIL, SmtpState.QUITTING),
    RCPT("RCPT", SmtpState.FROMSET, SmtpState.FROMSET, SmtpState.FROMSET),
    LASTRCPT("RCPT", SmtpState.TOSET, SmtpState.FROMSET, SmtpState.FROMSET),
    DATA("DATA", SmtpState.EMAILSEND, SmtpState.TOSET, SmtpState.TOSET),
    SENDEMAIL("SENDEMAIL", SmtpState.QUITTING, SmtpState.EMAILSEND, SmtpState.EMAILSEND),
    QUIT("QUIT", null, null, SmtpState.QUITTING, SmtpState.START,
            SmtpState.STARTEMAIL, SmtpState.FROMSET, SmtpState.TOSET, SmtpState.EMAILSEND);

    private static Logger logger = Logger.getLogger(SmtpAction.class.getName());
    private SmtpState[] allowedSmtpStates;
    private String requestName;
    private SmtpState ifSucceed;
    private SmtpState ifFailed;

    SmtpAction(String requestName, SmtpState ifSucceed,
                       SmtpState ifFailed, SmtpState... allowedSmtpStates) {
        this.allowedSmtpStates = allowedSmtpStates;
        this.requestName = requestName;
        this.ifSucceed = ifSucceed;
        this.ifFailed = ifFailed;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String response(SmtpState currentState)
            throws ErrorResponseServerException, UnrespondingServerException {
        byte[] response;
        String[] split;
        String message = "";
        try {
            response = ServerUtil.getInstance().receive();
            String str = ServerUtil.bytesToAsciiString(response);
            logger.info("Response : " + str);
            if (SmtpUtil.isAErrorResponse(currentState, str)) {
                message = str;
                throw new ErrorResponseServerException(
                        "Le serveur a retourné une erreur : " + message);
            } else if (SmtpUtil.isASuccessResponse(currentState, str)) {
                message = str;
                return message;
            } else if (str.equals(".")) {
            } else {
                try {
                    throw new BadFormatResponseServerException(
                            "La réponse du serveur n'a pas pu être interprétée : "
                                    + str);
                } catch (BadFormatResponseServerException e) {
                    logger.fatal(e.toString());
                    throw new RuntimeException(e.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public SmtpState[] getAllowedSmtpStates() {
        return allowedSmtpStates == null ?
                null :
                Arrays.copyOf(allowedSmtpStates,
                        allowedSmtpStates.length);
    }

    public String getRequestName() {
        return requestName;
    }

    public SmtpState getIfSucceed() {
        return ifSucceed;
    }

    public SmtpState getIfFailed() {
        return ifFailed;
    }

    public String execute(SmtpState smtpState, String... args)
            throws UnallowedActionException, ErrorResponseServerException,
            UnrespondingServerException {
        logger.info(String.format("==== %s started ====", name()));
        if (allowed(smtpState)) {
            request(args);
            String message = null;
            try {
                message = response(smtpState);
                logger.info(String.format("==== %s succeed ====", name()));
            } catch (ErrorResponseServerException e) {
                logger.info(String.format("==== %s failed ====", name()));
                throw e;
            }
            return message;
        } else {
            logger.info(String.format("==== %s failed ====", name()));
            throw new UnallowedActionException(
                    "L'action " + name() + " n'est pas autorisée dans l'état "
                            + (smtpState != null ?
                            smtpState.name() :
                            "CLOSED") + " !");
        }
    }

    private void request(String... args) throws UnrespondingServerException {
        try {
            if (this.equals(CONNEXION)) {
                if (args.length == 2)
                    ServerUtil.initialize(
                            new Server(args[0], Integer.parseInt(args[1])));
                else if (args.length == 3) {
                    ServerUtil.initialize(
                            new Server(args[0], Integer.parseInt(args[1])));
                }
            } else {
                Method method = SmtpUtil.class
                        .getMethod("getRequest" + requestName, String[].class);
                Object[] param = {args};
                String request = (String) method.invoke(null, param);
                logger.info(String.format("Request : %s",
                        request.replaceAll(Pop3Util.getEndRequest(), "")));
                ServerUtil.getInstance().send(request);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean allowed(SmtpState smtpState) {
        if ((allowedSmtpStates == null || allowedSmtpStates.length == 0)
                && smtpState == null)
            return Boolean.TRUE;
        for (SmtpState st : allowedSmtpStates) {
            if (st != null && st.equals(smtpState))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
