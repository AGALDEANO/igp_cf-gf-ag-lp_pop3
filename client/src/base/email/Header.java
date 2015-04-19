package base.email;

/**
 * @author Alexandre
 *         08/04/2015
 */
public enum Header {
    FROM("From"),
    TO("To"),
    SUBJECT("Subject"),
    DATE("Date"),
    MESSAGE_ID("Message-ID"),
    BCC("Bcc"),
    CC("Cc"),
    CONTENT_TYPE("Content-Type"),
    IN_REPLY_TO("In-Reply-To"),
    PRECEDENCE("Precedence"),
    RECEIVED("Received"),
    REFERENCES("References"),
    REPLY_TO("Reply-To"),
    SENDER("Sender"),
    RETURN_PATH("Return-Path"),
    ERROR_TO("Error-To");
    private String label;

    Header(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
