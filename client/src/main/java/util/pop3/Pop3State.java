package util.pop3;

import exception.UnallowedStateChangeException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public enum Pop3State {
    AUTHORIZATION(),
    USER_OK(AUTHORIZATION),
    TRANSACTION(AUTHORIZATION, USER_OK);
    private Pop3State[] previous;

    private Pop3State(Pop3State... previous) {
        this.previous = previous;
    }

    public static Pop3State changeTo(Pop3State from, Pop3State to) {
        // a null state equals a closed connection. It can happen at anytime
        if (to == null)
            return null;
        // a state can always change to itself
        if (to.equals(from))
            return to;
        // a closed connection can always lead to a state without any previous state
        if (from == null && (to.previous == null || to.previous.length == 0))
            return to;

        for (Pop3State st : to.previous) {
            if (st.equals(from)) {
                return to;
            }
        }
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