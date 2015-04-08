package base.email;

/**
 * @author Alexandre
 *         08/04/2015
 */
public enum Header {
    // TODO : Obtenir les bons headers
    OBJECT("OBJECT"),
    FROM("OBJECT"),
    CC("OBJECT"),
    CCI("OBJECT"),
    TO("TO");
    private String label;

    Header(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
