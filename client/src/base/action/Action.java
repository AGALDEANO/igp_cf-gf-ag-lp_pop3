package base.action;

import base.State;
import exception.BadFormatResponseServerException;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import org.apache.log4j.Logger;
import util.Pop3Util;
import util.ServerUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by alexandreg on 04/03/2015.
 */
public enum Action {
    APOP("APOP", State.TRANSACTION, State.AUTHORIZATION, State.AUTHORIZATION),
    USER("USER", State.USER_OK, State.AUTHORIZATION, State.AUTHORIZATION),
    PASS("PASS", State.TRANSACTION, State.AUTHORIZATION, State.USER_OK),
    RETR("RETR", State.TRANSACTION, State.TRANSACTION, State.TRANSACTION),
    STAT("STAT", State.TRANSACTION, State.TRANSACTION, State.TRANSACTION),
    QUIT("QUIT", null, null, null);
    private static Logger logger = Logger.getLogger(Action.class.getName());
    private State[] allowedStates;
    private String requestName;
    private State ifSucceed;
    private State ifFailed;

    private Action(String requestName, State ifSucceed, State ifFailed, State... allowedStates) {
        this.allowedStates = allowedStates;
        this.requestName = requestName;
        this.ifSucceed = ifSucceed;
        this.ifFailed = ifFailed;

    }

    public static Logger getLogger() {
        return logger;
    }

    public static String response() throws ErrorResponseServerException {
        byte[] response;
        String[] split;
        String message = "";
        try {
            response = ServerUtil.getInstance().receive();
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

    public State[] getAllowedStates() {
        return allowedStates;
    }

    public String getRequestName() {
        return requestName;
    }

    public State getIfSucceed() {
        return ifSucceed;
    }

    public State getIfFailed() {
        return ifFailed;
    }

    public String execute(State state, String... args) throws UnallowedActionException, ErrorResponseServerException {
        logger.info(String.format("==== %s started ====", this.name()));
        if (allowed(state)) {
            request(args);
            String message = null;
            try {
                message = response();
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
            Method method = Pop3Util.class.getMethod("getRequest" + requestName, String[].class);
            Object[] param = {args};
            String request = (String) method.invoke(null, param);
            logger.info(String.format("Request : %s", request.replaceAll(Pop3Util.getEndRequest(), "")));
            ServerUtil.getInstance().send(request);
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

    private Boolean allowed(State state) {
        if (allowedStates == null) return Boolean.TRUE;
        for (State st : allowedStates) {
            if (st == null && state == null) return Boolean.TRUE;
            else if (st != null && st.equals(state)) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
