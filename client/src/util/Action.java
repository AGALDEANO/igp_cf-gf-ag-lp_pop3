package util;

import base.Server;
import exception.BadFormatResponseServerException;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by alexandreg on 04/03/2015.
 */
public enum Action {
    CONNEXION("CONNEXION", CurrentState.AUTHORIZATION, null),
    APOP("APOP", CurrentState.TRANSACTION, CurrentState.AUTHORIZATION, CurrentState.AUTHORIZATION),
    USER("USER", CurrentState.USER_OK, CurrentState.AUTHORIZATION, CurrentState.AUTHORIZATION),
    PASS("PASS", CurrentState.TRANSACTION, CurrentState.AUTHORIZATION, CurrentState.USER_OK),
    RETR("RETR", CurrentState.TRANSACTION, CurrentState.TRANSACTION, CurrentState.TRANSACTION),
    STAT("STAT", CurrentState.TRANSACTION, CurrentState.TRANSACTION, CurrentState.TRANSACTION),
    LIST("LIST", CurrentState.TRANSACTION, CurrentState.TRANSACTION, CurrentState.TRANSACTION),
    QUIT("QUIT", null, null, CurrentState.AUTHORIZATION, CurrentState.TRANSACTION, CurrentState.USER_OK);
    private static Logger logger = Logger.getLogger(Action.class.getName());
    private CurrentState[] allowedCurrentStates;
    private String requestName;
    private CurrentState ifSucceed;
    private CurrentState ifFailed;

    private Action(String requestName, CurrentState ifSucceed, CurrentState ifFailed, CurrentState... allowedCurrentStates) {
        this.allowedCurrentStates = allowedCurrentStates;
        this.requestName = requestName;
        this.ifSucceed = ifSucceed;
        this.ifFailed = ifFailed;

    }

    public static Logger getLogger() {
        return logger;
    }

    public String response(Boolean isList) throws ErrorResponseServerException, UnrespondingServerException {
        byte[] response;
        String[] split;
        String message = "";
        try {
            response = (isList ? ServerUtil.getInstance().receiveList() : ServerUtil.getInstance().receive());
            String str = ServerUtil.bytesToAsciiString(response);
            logger.info("Response : " + str);
            if (str.startsWith(ServerUtil.errorResponse())) {
                split = str.split("\\" + ServerUtil.errorResponse() + ' ', 2);
                if (split.length > 1) message = split[1];
                throw new ErrorResponseServerException(message);
            } else if (str.startsWith(ServerUtil.successResponse())) {
                split = str.split("\\" + ServerUtil.successResponse() + ' ', 2);
                if (split.length > 1) message = split[1];
                return message;
            } else if (str.equals(".")) {
            } else {
                try {
                    throw new BadFormatResponseServerException(str);
                } catch (BadFormatResponseServerException e) {
                    logger.error(e.toString());
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public CurrentState[] getAllowedCurrentStates() {
        return allowedCurrentStates;
    }

    public String getRequestName() {
        return requestName;
    }

    public CurrentState getIfSucceed() {
        return ifSucceed;
    }

    public CurrentState getIfFailed() {
        return ifFailed;
    }

    public String execute(CurrentState currentState, String... args) throws UnallowedActionException, ErrorResponseServerException, UnrespondingServerException {
        logger.info(String.format("==== %s started ====", this.name()));
        if (allowed(currentState)) {
            request(args);
            String message = null;
            try {
                message = response((Action.LIST.equals(this) && (args == null || args.length == 0)) || Action.RETR.equals(this));
                logger.info(String.format("==== %s succeed ====", this.name()));
            } catch (ErrorResponseServerException e) {
                logger.info(String.format("==== %s failed ====", this.name()));
                throw e;
            }
            return message;
        } else {
            logger.info(String.format("==== %s failed ====", this.name()));
            throw new UnallowedActionException();
        }
    }

    private void request(String... args) {
        try {
            if (this.equals(CONNEXION) && args.length == 2) {
                ServerUtil.initialize(new Server(args[0], Integer.parseInt(args[1])));
            } else {
                Method method = Pop3Util.class.getMethod("getRequest" + requestName, String[].class);
                Object[] param = {args};
                String request = (String) method.invoke(null, param);
                logger.info(String.format("Request : %s", request.replaceAll(Pop3Util.getEndRequest(), "")));
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

    private Boolean allowed(CurrentState currentState) {
        if ((allowedCurrentStates == null || allowedCurrentStates.length == 0) && currentState == null)
            return Boolean.TRUE;
        for (CurrentState st : allowedCurrentStates) {
            if (st != null && st.equals(currentState)) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
