package util.smtp;

import exception.UnallowedStateChangeException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public enum SmtpState {
    START(),
    STARTEMAIL(START),
    FROMSET(STARTEMAIL),
    TOSET(FROMSET),
    EMAILSEND(TOSET),
    QUITTING(EMAILSEND);
    private SmtpState[] previous;

    private SmtpState(SmtpState... previous) {
        this.previous = previous;
    }

    public static SmtpState changeTo(SmtpState from, SmtpState to) {
        // a null state equals a closed connection. It can happen at anytime
        if (to == null)
            return null;
        // a state can always change to itself
        if (to.equals(from))
            return to;
        // a closed connection can always lead to a state without any previous state
        if (from == null && (to.previous == null || to.previous.length == 0))
            return to;

        for (SmtpState st : to.previous) {
            if (st.equals(from)) {
                return to;
            }
        }

        if (QUITTING.equals(from) && STARTEMAIL.equals(to)) return to;

        try {
            throw new UnallowedStateChangeException(
                    String.format("Can't change %s to %s.",
                            (from == null ? "CLOSED" : from.name()),
                            to.name()));
        } catch (UnallowedStateChangeException e) {
            e.printStackTrace();
        }
        return from;
    }
}