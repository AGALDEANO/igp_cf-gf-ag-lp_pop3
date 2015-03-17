package util;

import exception.MissingArgumentException;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pop3Util {
	private static Logger logger = Logger.getLogger(Pop3Util.class.getName());
	private static String endRequest = "\r\n";

	public static String getEndRequest() {
		return endRequest;
	}

	public static String getRequestAPOP(String[] args)
			throws MissingArgumentException {
		if (args.length == 2)
			return getRequestAPOP(args[0], args[1]);
		else if (args.length > 2)
			return getRequestAPOP(args[0], args[1], args[2]);
		else
			throw new MissingArgumentException();
	}

	public static String getRequestAPOP(String username, String password) {
		return getRequestAPOP(username, password, "");
	}

	public static String getRequestAPOP(String username, String password,
			String date) {
		try {
			byte[] digestPassword = MessageDigest.getInstance("MD5")
					.digest((password + date).getBytes("ascii"));
			StringBuilder request = new StringBuilder("APOP " + username + " ");
			for (byte b : digestPassword) {
				request.append(String.format("%x", b));
			}
			request.append(endRequest);
			return request.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.toString());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		return null;
	}

	public static String getRequestUSER(String[] args)
			throws MissingArgumentException {
		if (args.length > 0)
			return getRequestUSER(args[0]);
		else
			throw new MissingArgumentException();
	}

	public static String getRequestUSER(String username) {
		String request = "USER " + username;
		request += endRequest;
		return request;
	}

	public static String getRequestPASS(String[] args)
			throws MissingArgumentException {
		if (args.length > 0)
			return getRequestPASS(args[0]);
		else
			throw new MissingArgumentException();
	}

	public static String getRequestPASS(String password) {
		String request = "PASS " + password;
		request += endRequest;
		return request;
	}

	public static String getRequestRETR(String[] args)
			throws MissingArgumentException {
		if (args.length > 0)
			return getRequestRETR(Integer.parseInt(args[0]));
		else
			throw new MissingArgumentException();
	}

	public static String getRequestRETR(int numeroMessage) {
		String request = "RETR " + numeroMessage;
		request += endRequest;
		return request;
	}

	public static String getRequestQUIT(String[] args)
			throws MissingArgumentException {
		return getRequestQUIT();
	}

	public static String getRequestQUIT() {
		String request = "QUIT";
		request += endRequest;
		return request;
	}

	public static String getRequestLIST(String[] args)
			throws MissingArgumentException {
		return (args.length == 0 ?
				getRequestLIST() :
				getRequestLIST(Integer.parseInt(args[0])));
	}

	public static String getRequestLIST() {
		String request = "LIST";
		request += endRequest;
		return request;
	}

	public static String getRequestLIST(int i) {
		String request = "LIST " + i;
		request += endRequest;
		return request;
	}

	public static String getRequestSTAT(String[] args)
			throws MissingArgumentException {
		return getRequestSTAT();
	}

	public static String getRequestSTAT() {
		String request = "STAT";
		request += endRequest;
		return request;
	}
}
