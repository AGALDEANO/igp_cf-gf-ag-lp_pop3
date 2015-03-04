package util;

import exception.UnallowedStateChangeException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public enum CurrentState {
    AUTHORIZATION(),
    USER_OK(AUTHORIZATION),
    TRANSACTION(AUTHORIZATION, USER_OK),
    CLOSED(AUTHORIZATION, TRANSACTION);
    private CurrentState[] previous;

    private CurrentState(CurrentState... previous) {
        this.previous = previous;
    }

    public CurrentState changeTo(CurrentState currentState) {
        if (currentState == null) return null;
        if (this.equals(currentState)) return currentState;
        else
            for (CurrentState st : currentState.previous) {
                if (this.equals(st)) {
                    return currentState;
            }
        }
        try {
            throw new UnallowedStateChangeException(String.format("Can't change %s to %s.", this.name(), currentState.name()));
        } catch (UnallowedStateChangeException e) {
            e.printStackTrace();
        }
        return this;
    }
}