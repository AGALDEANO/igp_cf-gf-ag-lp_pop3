package base;

import exceptions.BadResponseServerException;
import exceptions.ErrorResponseServerException;
import exceptions.UnallowedActionClientException;
import util.Pop3Util;
import util.ServerUtil;

import java.io.IOException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Client {
    private State state;
    private ServerUtil serverUtil;
    private int unreadMessage;

    public Client() {
    }

    public Boolean connexion(String hostname, int port) {
        if (state == null || State.CLOSED.equals(state)) {
            connexionRequest(hostname, port);
            if (connexionResponse()) {
                state = State.AUTH;
                return Boolean.TRUE;
            }
        }
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
            e.printStackTrace();
        } catch (BadResponseServerException e) {
            e.printStackTrace();
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
    public Boolean signIn(String username, String password) {
        if (state.equals(State.AUTH)) {
            signInRequest(username, password);
            unreadMessage = signInResponse();
            if (unreadMessage > -1) {
                state.changeTo(State.TRANSACTION);
                return Boolean.TRUE;
            }
        } else new UnallowedActionClientException();

        return Boolean.FALSE;
    }

    private void signInRequest(String username, String password) {
        try {
            serverUtil.send(Pop3Util.getrequestAPOP(username, password));
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (BadResponseServerException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Return true if quit success
     * else return false
     *
     * @return
     */
    public Boolean quit() {
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
            serverUtil.send(Pop3Util.getrequestQUIT());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean quitResponse() {

        return Boolean.FALSE;
    }

    private String getResponse() throws ErrorResponseServerException, BadResponseServerException {
        byte[] response = new byte[0];
        try {
            response = serverUtil.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = ServerUtil.bytesToAsciiString(response);
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
