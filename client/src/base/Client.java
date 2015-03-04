package base;

import exception.BadFormatResponseServerException;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import org.apache.log4j.Logger;
import util.Pop3Util;
import util.ServerUtil;

import java.io.IOException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Client {
    private static Logger logger = Logger.getLogger(Client.class.getName());
    private State state;
    private ServerUtil serverUtil;
    private int unreadMessage = 0;

    public Client() {
    }

    /**
     * Try to connect using 'user:pw@host:port'
     *
     * @param email
     * @return
     * @throws exception.UnallowedActionException
     */
    public Boolean connexionAction(String email) throws UnallowedActionException {
        String[] emailParts = email.split("@", 2);
        String[] user = emailParts[0].split(":", 2);
        String[] host = emailParts[1].split(":", 2);
        if (connexion(host[0], Integer.parseInt(host[1]))) {
            return signIn(user[0], user[1]);
        }
        return Boolean.FALSE;
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
    private Boolean connexion(String hostname, int port) throws UnallowedActionException {
        logger.info("===========================");
        logger.info("==== Connexion started ====");
        if (state == null || State.CLOSED.equals(state)) {
            connexionRequest(hostname, port);
            if (connexionResponse()) {
                state = State.AUTHORIZATION;
                logger.info("==== Connexion succeed ====");
                return Boolean.TRUE;
            }
        } else {
            logger.warn("==== Connexion failed ====");
            throw new UnallowedActionException();
        }
        logger.warn("==== Connexion failed ====");
        return Boolean.FALSE;
    }

    private void connexionRequest(String hostname, int port) {
        ServerUtil.initialize(new Server(hostname, port));
        serverUtil = ServerUtil.getInstance();
    }

    private Boolean connexionResponse() {
        logger.info("==== Connexion response ====");
        try {
            String message = getResponse();
            return Boolean.TRUE;
        } catch (ErrorResponseServerException e) {
            logger.warn(e.toString());
        } catch (BadFormatResponseServerException e) {
            logger.error(e.toString());
            System.exit(-1);
        } catch (IOException e) {
            logger.error(e.toString());
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
    private Boolean signIn(String username, String password) throws UnallowedActionException {
        logger.info("==================================");
        logger.info("==== Authentification started ====");
        if (State.AUTHORIZATION.equals(state)) {
            if (serverUtil.getServer().getApop()) signInRequestApop(username, password);
            else signInRequest(username, password);
            unreadMessage = signInResponse();
            if (unreadMessage > -1) {
                state.changeTo(State.TRANSACTION);
                return Boolean.TRUE;
            }
        } else throw new UnallowedActionException();

        return Boolean.FALSE;
    }

    private void signInRequestApop(String username, String password) {
        try {
            logger.info(String.format("Attempting to connect to %s@%s", username, serverUtil.getServer().getHostname()));
            String request = Pop3Util.getRequestAPOP(username, password);
            serverUtil.send(request);
            logger.info("Request : " + request);
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    private void signInRequest(String username, String password) {
        try {
            logger.info(String.format("Attempting to connect to %s@%s", username, serverUtil.getServer().getHostname()));
            String request = Pop3Util.getRequestUSER(username);
            serverUtil.send(request);
            logger.info("Request : " + request);
            String message = getResponse();
            request = Pop3Util.getRequestPASS(password);
            serverUtil.send(request);
            logger.info("Request : " + request);
        } catch (IOException e) {
            logger.warn(e.toString());
        } catch (BadFormatResponseServerException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
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
            logger.warn(e.toString());
        } catch (BadFormatResponseServerException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return -1;
    }

    private void statRequest() {
        try {
            logger.info("Obtaining details");
            String request = Pop3Util.getRequestSTAT();
            logger.info("Request : " + request);
            serverUtil.send(request);
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    /**
     * If sign in failed return -1
     * return the amount of unread message
     *
     * @return
     * @throws IOException
     */
    private int statResponse() {
        try {
            String message = getResponse();
            // N number of message, M number of bytes
            // message = N M
            message = message.split(" ", 2)[0];
            return Integer.parseInt(message);
        } catch (ErrorResponseServerException e) {
            logger.warn(e.toString());
        } catch (BadFormatResponseServerException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
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
        if (!State.CLOSED.equals(state)) {
            quitRequest();
            if (quitResponse()) {
                state.changeTo(State.CLOSED);
                return Boolean.TRUE;
            }
        } else new UnallowedActionException();

        return Boolean.FALSE;
    }

    private void quitRequest() {
        try {
            String request = Pop3Util.getRequestQUIT();
            serverUtil.send(request);
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    private Boolean quitResponse() {
        try {
            String message = getResponse();
            return Boolean.TRUE;
        } catch (ErrorResponseServerException e) {
            logger.warn(e.toString());
        } catch (BadFormatResponseServerException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return Boolean.FALSE;
    }


    /**
     * Return true if quit success
     * else return false
     *
     * @return
     */
    public String retreiveAction(int i) throws UnallowedActionException {
        if (State.TRANSACTION.equals(state) && i > 0 && i <= unreadMessage) {
            retreiveRequest(i);
            return retreiveResponse();
        } else throw new UnallowedActionException();
    }

    private void retreiveRequest(int i) {
        try {
            String request = Pop3Util.getRequestRETR(i);
            serverUtil.send(request);
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    private String retreiveResponse() {
        try {
            String message = getResponse();
            return message;
        } catch (ErrorResponseServerException e) {
            logger.warn(e.toString());
        } catch (BadFormatResponseServerException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private String getResponse() throws ErrorResponseServerException, BadFormatResponseServerException, IOException {
        byte[] response;
        String[] split;
        String message = "";
        response = serverUtil.receive();
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
            throw new BadFormatResponseServerException(str);
        }
    }
}
