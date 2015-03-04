package util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pop3Util {
    private static Logger logger = Logger.getLogger(Pop3Util.class.getName());

    private static String endRequest = "\r\n";

    public static String getrequestAPOP(String username, String password) {
        try {
            byte[] digestPassword = MessageDigest.getInstance("MD5").digest(password.getBytes());
            String request = "APOP " + username + " ";
            for (byte b : digestPassword) {
                request += String.format("%x", b);
            }
            request += endRequest;
            return request;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString());
        }
        return null;
    }

    public static String getrequestUSER(String username) {
        String request = "USER " + username;
        request += endRequest;
        return request;
    }

    public static String getrequestPASS(String password) {
        try {
            byte[] digestPassword = MessageDigest.getInstance("MD5").digest(password.getBytes());
            String request = "PASS ";
            for (byte b : digestPassword) {
                request += String.format("%x", b);
            }
            request += endRequest;
            return request;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString());
        }
        return null;
    }

    public static String getrequestRETR(int numeroMessage) {
        String request = "RETR " + numeroMessage;
        request += endRequest;
        return request;
    }

    public static String getrequestQUIT() {
        String request = "QUIT";
        request += endRequest;
        return request;
    }

    public static String getrequestSTAT() {
        String request = "STAT";
        request += endRequest;
        return request;
    }
}
