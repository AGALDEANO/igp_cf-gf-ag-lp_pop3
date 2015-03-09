package base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandreg on 09/03/2015.
 */
public class Email {
    private static String endline = "\r\n";
    private static String endHeader = "\r\n\r\n";
    private static String headerSeparator = ": ";
    private static String endFile = "\r\n.\r\n";
    private HashMap<String, String> headers;
    private String body;

    public Email(String response) {
        String[] splittedResponse = response.split(endHeader);
        String[] strHeader = splittedResponse[0].split(endline);
        body = splittedResponse[1].split(endFile)[0];
        for (String header : strHeader) {
            headers.put(header.split(headerSeparator)[0], header.split(headerSeparator)[1]);
        }
    }

    public Email(Email e) {
        body = e.getBody();
        headers = (HashMap<String, String>) e.getHeaders().clone();
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
            str += key + headerSeparator + endline;
        }
        str += endline;
        str += body;

        return str;
    }
}
