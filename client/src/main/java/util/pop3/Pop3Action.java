package util.pop3;

import base.Server;
import exception.BadFormatResponseServerException;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;
import util.ServerUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by alexandreg on 04/03/2015.
 */
public enum Pop3Action {
    CONNEXION("CONNEXION", Pop3State.AUTHORIZATION, null),
    APOP("APOP", Pop3State.TRANSACTION, Pop3State.AUTHORIZATION,
            Pop3State.AUTHORIZATION),
    USER("USER", Pop3State.USER_OK, Pop3State.AUTHORIZATION,
            Pop3State.AUTHORIZATION),
    PASS("PASS", Pop3State.TRANSACTION, Pop3State.AUTHORIZATION,
            Pop3State.USER_OK),
    RETR("RETR", Pop3State.TRANSACTION, Pop3State.TRANSACTION,
            Pop3State.TRANSACTION),
    STAT("STAT", Pop3State.TRANSACTION, Pop3State.TRANSACTION,
            Pop3State.TRANSACTION),
    LIST("LIST", Pop3State.TRANSACTION, Pop3State.TRANSACTION,
            Pop3State.TRANSACTION),
    QUIT("QUIT", null, null, Pop3State.AUTHORIZATION,
            Pop3State.TRANSACTION, Pop3State.USER_OK);
    private static Logger logger = Logger.getLogger(Pop3Action.class.getName());
    private Pop3State[] allowedPop3States;
    private String requestName;
    private Pop3State ifSucceed;
    private Pop3State ifFailed;

    private Pop3Action(String requestName, Pop3State ifSucceed,
                       Pop3State ifFailed, Pop3State... allowedPop3States) {
        this.allowedPop3States = allowedPop3States;
        this.requestName = requestName;
        this.ifSucceed = ifSucceed;
        this.ifFailed = ifFailed;

    }

    public static Logger getLogger() {
        return logger;
    }

    public String response(Boolean isList)
            throws ErrorResponseServerException, UnrespondingServerException {
        byte[] response;
        String[] split;
        String message = "";
        try {
            response = (isList ?
                    ServerUtil.getInstance().receiveMultiline() :
                    ServerUtil.getInstance().receive());
            String str = ServerUtil.bytesToAsciiString(response);
            logger.info("Response : " + str);
            if (str.startsWith(ServerUtil.errorResponse())) {
                split = str.split("\\" + ServerUtil.errorResponse() + ' ', 2);
                if (split.length > 1)
                    message = split[1];
                throw new ErrorResponseServerException(
                        "Le serveur a retourné une erreur : " + message);
            } else if (str.startsWith(ServerUtil.successResponse())) {
                split = str.split("\\" + ServerUtil.successResponse() + ' ', 2);
                if (split.length > 1)
                    message = split[1];
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

    public Pop3State[] getAllowedPop3States() {
        return allowedPop3States == null ?
                null :
                Arrays.copyOf(allowedPop3States,
                        allowedPop3States.length);
    }

    public String getRequestName() {
        return requestName;
    }

    public Pop3State getIfSucceed() {
        return ifSucceed;
    }

    public Pop3State getIfFailed() {
        return ifFailed;
    }

    public String execute(Pop3State pop3State, String... args)
            throws UnallowedActionException, ErrorResponseServerException,
            UnrespondingServerException {
        logger.info(String.format("==== %s started ====", name()));
        if (allowed(pop3State)) {
            request(args);
            String message = null;
            try {
                message = response((Pop3Action.LIST.equals(this) && (args == null
                        || args.length == 0)) || Pop3Action.RETR.equals(this));
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
                            + (pop3State != null ?
                            pop3State.name() :
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
                Method method = Pop3Util.class
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

    private Boolean allowed(Pop3State pop3State) {
        if ((allowedPop3States == null || allowedPop3States.length == 0)
                && pop3State == null)
            return Boolean.TRUE;
        for (Pop3State st : allowedPop3States) {
            if (st != null && st.equals(pop3State))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
