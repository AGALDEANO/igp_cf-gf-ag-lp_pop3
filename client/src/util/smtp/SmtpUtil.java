package util.smtp;

import exception.MissingArgumentException;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SmtpUtil {
    private static Logger logger = Logger.getLogger(SmtpUtil.class.getName());
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
                                        String timestamp) {
        try {
            byte[] digestPassword = MessageDigest.getInstance("MD5")
                    .digest((password + timestamp).getBytes("ascii"));
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

    public static String getRequestEHLO(String[] args)
            throws MissingArgumentException {
        if (args.length > 0)
            return getRequestEHLO(args[0]);
        else
            throw new MissingArgumentException();
    }

    public static String getRequestEHLO(String identifiant) {
        String request = "EHLO " + identifiant;
        request += endRequest;
        return request;
    }

    public static String getRequestMAILFROM(String[] args)
            throws MissingArgumentException {
        if (args.length > 0)
            return getRequestMAILFROM(args[0]);
        else
            throw new MissingArgumentException();
    }

    public static String getRequestMAILFROM(String emailAddress) {
        String request = "MAIL FROM: <" + emailAddress + '>';
        request += endRequest;
        return request;
    }

    public static String getRequestRCPT(String[] args)
            throws MissingArgumentException {
        if (args.length > 0)
            return getRequestRCPT(args[0]);
        else
            throw new MissingArgumentException();
    }

    public static String getRequestRCPT(String emailAddress) {
        String request = "RCPT TO: <" + emailAddress + '>';
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

    public static String getRequestDATA(String[] args)
            throws MissingArgumentException {
        return getRequestDATA();
    }

    public static String getRequestDATA() {
        String request = "DATA";
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
