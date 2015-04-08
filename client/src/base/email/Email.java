package base.email;

import java.util.Date;
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
	private long id;
	private int bytes;
	private HashMap<String, String> headers = new HashMap<>();
	private String body;

	public Email(String response) {
		id = new Date().getTime();
		String[] splittedResponse = response.split(endHeader);
		String[] strHeader = splittedResponse[0].split(endLine);
		body = splittedResponse[1].split(endFile)[0];
		if (strHeader.length > 0) {
			bytes = Integer.parseInt(strHeader[0]);
			for (int i = 1; i < strHeader.length; i++) {
				String header = strHeader[i];
				headers.put(header.split(headerSeparator)[0],
						header.split(headerSeparator)[1]);
			}
		}
	}
	public Email(String response, long id) {
		this.id = id;
		String[] splittedResponse = response.split(endHeader);
		String[] strHeader = splittedResponse[0].split(endLine);
		body = splittedResponse[1].split(endFile)[0];
		if (strHeader.length > 0) {
			bytes = Integer.parseInt(strHeader[0]);
			for (int i = 1; i < strHeader.length; i++) {
				String header = strHeader[i];
				headers.put(header.split(headerSeparator)[0],
						header.split(headerSeparator)[1]);
			}
		}
	}

	public Email(Email e) {
		id = e.getId();
		bytes = e.getBytes();
		body = e.getBody();
		Object clone = e.getHeaders().clone();
		if (clone instanceof HashMap<?, ?>) {
			HashMap hashMap = (HashMap) clone;
			headers = hashMap;
		} else {
			headers = new HashMap<String, String>();
		}
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

	public int getBytes() {
		return bytes;
	}

	public long getId() {
		return id;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}

	public String headersToString() {
		String str = Integer.toString(bytes) + "\r\n";
		StringBuffer sb = new StringBuffer(str);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			sb.append(entry.getKey());
			sb.append(headerSeparator);
			sb.append(entry.getValue());
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

	@Override public String toString() {
		return headersToString() + endLine + bodyToString() + footerToString();
	}
}
