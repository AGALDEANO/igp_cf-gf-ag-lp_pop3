package base;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Client {
    private State state;

    public Client(String hostname, int port) {

        state = State.CLOSED;
    }
}
