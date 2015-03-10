package base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandreg on 09/03/2015.
 */
public class Email {
    private static String endLine = "\r\n";
    private static String endHeader = "\r\n\r\n";
    private static String headerSeparator = ": ";
    private static String endFile = "\r\n.\r\n";
    private int bytes;
    private HashMap<String, String> headers = new HashMap<>();
    private String body;

    public Email(String response) {
        String[] splittedResponse = response.split(endHeader);
        String[] strHeader = splittedResponse[0].split(endLine);
        body = splittedResponse[1].split(endFile)[0];
        if (strHeader.length > 0) {
            bytes = Integer.parseInt(strHeader[0]);
            for (int i = 1; i < strHeader.length; i++) {
                String header = strHeader[i];
                headers.put(header.split(headerSeparator)[0], header.split(headerSeparator)[1]);
            }
        }
    }

    public Email(Email e) {
        body = e.getBody();
        Object clone = e.getHeaders().clone();
        if (clone instanceof HashMap<?, ?>) {
            HashMap hashMap = (HashMap) clone;
            headers = hashMap;
        } else {
            headers = new HashMap<String, String>();
        }
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String headersToString() {
        String str = "";
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            str += key + headerSeparator + value + endLine;
        }

        return str;
    }

    public String bodyToString() {
        return body;
    }

    @Override
    public String toString() {
        return headersToString() + endLine + bodyToString();
    }
}
