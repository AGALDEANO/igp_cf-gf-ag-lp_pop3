package base;

import exception.UnallowedStateChangeException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public enum State {
    AUTHORIZATION(),
    USER_OK(AUTHORIZATION),
    TRANSACTION(AUTHORIZATION, USER_OK),
    CLOSED(AUTHORIZATION, TRANSACTION);
    private State[] previous;

    private State(State... previous) {
        this.previous = previous;
    }

    public State changeTo(State state) {
        if (state == null) return null;
        if (this.equals(state)) return state;
        else
            for (State st : state.previous) {
                if (this.equals(st)) {
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