package base.email;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexandreg on 09/03/2015.
 */
public class Email {
    private static String endLine = "\r\n";
    private static String endHeader = "\r\n\r\n";
    private static String headerSeparator = ": ";
    private static String endFile = "\r\n.\r\n";
    private long id;
    private ArrayList<EmailHeader> headers = new ArrayList<>();
    private String body;

    public Email(String response) {
        id = new Date().getTime();
        String[] splittedResponse = response.split(endHeader);
        String[] strHeader = splittedResponse[0].split(endLine);
        body = splittedResponse.length == 1 ? "" : splittedResponse[1].split(endFile)[0];
        if (strHeader.length > 0) {
            for (String header : strHeader) {
                EmailHeader eh = getHeaderFromString(header);
                if (eh != null) {
                    headers.add(eh);
                    if (Header.MESSAGE_ID.equals(eh.getHeader())) id = Long.parseLong(eh.getValue(), 16);
                }

            }
        }
    }

    public Email(String body, EmailHeader... emailHeaders) {
        this.body = body.replaceAll("\r\n", "\n")
                .replaceAll("\n", "\r\n")
                .replaceAll("\r\n.\r\n", "\r\n.\n");
        for (EmailHeader emailHeader : emailHeaders) {
            this.headers.add(emailHeader);
        }
    }

    @Deprecated
    public Email(String response, long id) {
        this.id = id;
        String[] splittedResponse = response.split(endHeader);
        String[] strHeader = splittedResponse[0].split(endLine);
        body = splittedResponse[1].split(endFile)[0];
        if (strHeader.length > 0) {
            for (int i = 1; i < strHeader.length; i++) {
                String header = strHeader[i];
                Header h = null;
                EmailHeader eh = getHeaderFromString(header);
                if (eh != null) headers.add(eh);
            }
        }
    }

    public Email(Email e) {
        id = e.getId();
        body = e.getBody();
        headers = (ArrayList<EmailHeader>) e.getHeaders().clone();
    }

    public static String getEndLine() {
        return endLine;
    }

    public static String getEndHeader() {
        return endHeader;
    }

    public static String getHeaderSeparator() {
        return headerSeparator;
    }

    public static String getEndFile() {
        return endFile;
    }

    public long getId() {
        return id;
    }

    public ArrayList<EmailHeader> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String headersToString() {
        StringBuffer sb = new StringBuffer("");
        for (EmailHeader emailHeader : headers) {
            sb.append(emailHeader.getHeader().getLabel());
            sb.append(headerSeparator);
            sb.append(emailHeader.getValue());
            sb.append(endLine);
        }

        return sb.toString();
    }

    public String bodyToString() {
        return body;
    }

    public String footerToString() {
        return endFile;
    }

    @Override
    public String toString() {
        return headersToString() + endLine + bodyToString() + footerToString();
    }

    private EmailHeader getHeaderFromString(String str) {
        Header h = null;
        if (str.split(headerSeparator).length == 1) return null;
        String hstr = str.split(headerSeparator)[0];
        for (Header header : Header.values())
            if (header.getLabel().equals(hstr)) {
                h = header;
                break;
            }
        return new EmailHeader(h,
                str.split(headerSeparator)[1]);
    }
}
