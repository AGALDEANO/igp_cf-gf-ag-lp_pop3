package base;

import exceptions.BadResponseServerException;
import exceptions.ErrorResponseServerException;
import exceptions.UnallowedActionClientException;
import org.apache.log4j.Logger;
import util.Pop3Util;
import util.ServerUtil;

import java.io.IOException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Client {
    private static Logger log = Logger.getLogger(
            Client.class.getName());
    private State state;
    private ServerUtil serverUtil;
    private int unreadMessage = 0;

    public Client() {
    }

    /**
     * Attempts to connect at the hostname
     * with the port specified
     * Return false if it failed
     *
     * @param hostname
     * @param port
     * @return
     */
    public Boolean connexionAction(String hostname, int port) throws UnallowedActionClientException {
        if (state == null || State.CLOSED.equals(state)) {
            connexionRequest(hostname, port);
            if (connexionResponse()) {
                state = State.AUTH;
                return Boolean.TRUE;
            }
        } else throw new UnallowedActionClientException();
        return Boolean.FALSE;
    }

    private void connexionRequest(String hostname, int port) {
        serverUtil = ServerUtil.getInstance(new Server(hostname, port));
    }

    private Boolean connexionResponse() {
        try {
            String message = getResponse();
            return Boolean.TRUE;
        } catch (ErrorResponseServerException e) {
            log.warn(e.toString());
        } catch (BadResponseServerException e) {
            log.error(e.toString());
        }
        return Boolean.FALSE;
    }

    /**
     * Attempts to sign in to the server
     * return true if it succeed, else
     * return false
     *
     * @param username
     * @param password
     * @return
     */
    public Boolean signInAction(String username, String password) throws UnallowedActionClientException {
        if (state.equals(State.AUTH)) {
            signInRequest(username, password);
            unreadMessage = signInResponse();
            if (unreadMessage > -1) {
                state.changeTo(State.TRANSACTION);
                return Boolean.TRUE;
            }
        } else throw new UnallowedActionClientException();

        return Boolean.FALSE;
    }

    private void signInRequest(String username, String password) {
        try {
            String request = Pop3Util.getrequestAPOP(username, password);
            serverUtil.send(request);
            log.info("Request : " + request);
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }

    /**
     * If sign in failed return -1
     * return the amount of unread message
     *
     * @return
     * @throws IOException
     */
    private int signInResponse() {
        try {
            String message = getResponse();
            // message = +OK XXX’s maildrop has N messages (YYY bytes)
            message = message.split("has ", 2)[1];
            // message = N messages (YYY bytes)
            message = message.split(" ", 2)[0];
            // message = N
            return Integer.parseInt(message);
        } catch (ErrorResponseServerException e) {
            log.warn(e.toString());
        } catch (BadResponseServerException e) {
            log.error(e.toString());
        }
        return -1;
    }

    /**
     * Return true if quit success
     * else return false
     *
     * @return
     */
    public Boolean quitAction() {
        if (!state.equals(State.CLOSED)) {
            quitRequest();
            if (quitResponse()) {
                state.changeTo(State.CLOSED);
                return Boolean.TRUE;
            }
        } else new UnallowedActionClientException();

        return Boolean.FALSE;
    }

    public void quitRequest() {
        try {
            String request = Pop3Util.getrequestQUIT();
            serverUtil.send(request);
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }

    public Boolean quitResponse() {
        try {
            String message = getResponse();
            return Boolean.TRUE;
        } catch (ErrorResponseServerException e) {
            log.warn(e.toString());
        } catch (BadResponseServerException e) {
            log.error(e.toString());
        }
        return Boolean.FALSE;
    }


    /**
     * Return true if quit success
     * else return false
     *
     * @return
     */
    public String retreiveAction(int i) throws UnallowedActionClientException {
        if (state.equals(State.TRANSACTION) && i > 0 && i <= unreadMessage) {
            retreiveRequest(i);
            return retreiveResponse();
        } else throw new UnallowedActionClientException();
    }

    public void retreiveRequest(int i) {
        try {
            String request = Pop3Util.getrequestRETR(i);
            serverUtil.send(request);
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }

    public String retreiveResponse() {
        try {
            String message = getResponse();
            return message;
        } catch (ErrorResponseServerException e) {
            log.warn(e.toString());
        } catch (BadResponseServerException e) {
            log.error(e.toString());
        }
        return null;
    }

    private String getResponse() throws ErrorResponseServerException, BadResponseServerException {
        byte[] response = new byte[0];
        try {
            response = serverUtil.receive();
        } catch (IOException e) {
            log.warn(e.toString());
        }
        String str = ServerUtil.bytesToAsciiString(response);
        log.info("Response : " + str);
        if (str.startsWith(ServerUtil.errorResponse())) {
            String message = str.split(ServerUtil.errorResponse() + ' ', 2)[1];
            throw new ErrorResponseServerException(message);
        } else if (str.startsWith(ServerUtil.successResponse())) {
            String message = str.split(ServerUtil.successResponse() + ' ', 2)[1];
            return message;
        } else {
            throw new BadResponseServerException(str);
        }
    }
}
