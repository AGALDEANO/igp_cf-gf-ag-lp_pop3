package base.email;

/**
 * @author Alexandre
 *         08/04/2015
 */
public class EmailHeader {
    private Header header;
    private String value;

    public EmailHeader(Header header, String value) {
        this.header = header;
        this.value = value;
    }

    public Header getHeader() {
        return header;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return header.getLabel() + Email.getHeaderSeparator() + value;
    }
}
