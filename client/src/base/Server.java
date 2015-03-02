package base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Server {
    private InetAddress inetAddress;
    private String hostname;
    private int port = 110;
    private Socket socket;

    public Server(String hostname, int port) {
        if (port >= 0) {
            this.port = port;

            if (hostname == null) throw new NullPointerException();
            this.hostname = hostname;

            try {
                updateAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            try {
                updateDatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } else try {
            throw new WrongPortServerException();
        } catch (WrongPortServerException e) {
            e.printStackTrace();
        }

    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        if (hostname == null) throw new NullPointerException();
        this.hostname = hostname;
        try {
            updateAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            updateDatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if (port >= 0) {
            this.port = port;
            try {
                updateDatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    private void updateAddress() throws UnknownHostException {
        inetAddress = InetAddress.getByName(hostname);
    }

    private void updateDatagramSocket() throws SocketException {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket = new Socket(inetAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WrongPortServerException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public WrongPortServerException() {
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
    public WrongPortServerException(String message) {
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
    public WrongPortServerException(String message, Throwable cause) {
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
    public WrongPortServerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted,
     *                           and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     * @since 1.7
     */
    protected WrongPortServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}