package base;

import util.Pop3Util;
import util.ServerUtil;

import java.io.IOException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Client {
    private State state;
    private ServerUtil serverUtil;

    public Client(String hostname, int port) {
        serverUtil = ServerUtil.getInstance(new Server(hostname, port));
    }

    public void signIn(String username, String password) throws IOException {
        if (state.equals(State.AUTH)) {
            serverUtil.send(Pop3Util.getrequestAPOP(username, password));
        } else new UnallowedActionClientException();
    }

    public void quit() throws IOException {
        if (!state.equals(State.CLOSED)) {
            serverUtil.send(Pop3Util.getrequestQUIT());
        } else new UnallowedActionClientException();
    }
}

class UnallowedActionClientException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UnallowedActionClientException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UnallowedActionClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public UnallowedActionClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public UnallowedActionClientException(Throwable cause) {
        super(cause);
    }
}