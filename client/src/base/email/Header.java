package base.email;

/**
 * @author Alexandre
 *         08/04/2015
 */
public enum Header {
    // TODO : Obtenir les bons headers
    SUBJECT("Subject"),
    FROM("From"),
    CC("OBJECT"),
    CCI("OBJECT"),
    TO("To");
    private String label;

    Header(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
