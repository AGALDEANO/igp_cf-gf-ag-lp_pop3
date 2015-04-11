package base.client;

/**
 * @author Alexandre
 *         08/04/2015
 */
public enum Port {
    POP3(110),
    POP3_SSL(995),
    SMTP(25);
    private int value;

    Port(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
