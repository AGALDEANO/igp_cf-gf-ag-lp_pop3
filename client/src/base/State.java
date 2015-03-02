package base;

/**
 * Created by alexandreg on 02/03/2015.
 */
public enum State {
    AUTH(),
    TRANSACTION(State.AUTH),
    CLOSED(State.AUTH, State.TRANSACTION);
    private State[] previous;

    private State(State... previous) {
        this.previous = previous;
    }

    public State changeTo(State state) {
        for (State st : previous) {
            if (st.equals(state)) {
                return state;
            }
        }
        try {
            throw new UnallowedStateChangeException(String.format("Can't change %s to %s.", this.name(), state.name()));
        } catch (UnallowedStateChangeException e) {
            e.printStackTrace();
        }
        return this;
    }
}

class UnallowedStateChangeException extends Exception {
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
    public UnallowedStateChangeException(Throwable cause) {
        super(cause);
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
    public UnallowedStateChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UnallowedStateChangeException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UnallowedStateChangeException() {
        super();
    }
}
