package util;

import exception.UnallowedStateChangeException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public enum CurrentState {
	AUTHORIZATION(),
	USER_OK(AUTHORIZATION),
	TRANSACTION(AUTHORIZATION, USER_OK);
	private CurrentState[] previous;

	private CurrentState(CurrentState... previous) {
		this.previous = previous;
	}

	public static CurrentState changeTo(CurrentState from, CurrentState to) {
		// a null state equals a closed connection. It can happen at anytime
		if (to == null)
			return null;
		// a state can always change to itself
		if (to.equals(from))
			return to;
		// a closed connection can always lead to a state without any previous state
		if (from == null && (to.previous == null || to.previous.length == 0))
			return to;

		for (CurrentState st : to.previous) {
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