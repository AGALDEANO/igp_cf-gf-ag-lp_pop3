package util.smtp;

import exception.MissingArgumentException;
import org.apache.log4j.Logger;

public class SmtpUtil {
    private static Logger logger = Logger.getLogger(SmtpUtil.class.getName());
    private static String endRequest = "\r\n";

    public static String getEndRequest() {
        return endRequest;
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

    public static String getRequestSENDEMAIL(String[] args)
            throws MissingArgumentException {
        if (args.length != 1) throw new MissingArgumentException();
        return getRequestSENDEMAIL(args[0]);
    }

    public static String getRequestSENDEMAIL(String email) {
        String request = email;
        return request;
    }

    public static Boolean isASuccessResponse(SmtpState smtpState, String response) {
        Integer code = getIntegerPrefix(response);
        if (code == null) return Boolean.FALSE;
        if (smtpState == null) return code == 220;
        if (SmtpState.START.equals(smtpState)) return code == 250;
        if (SmtpState.STARTEMAIL.equals(smtpState)) return code == 250;
        if (SmtpState.FROMSET.equals(smtpState)) return code == 250 || code == 553;
        if (SmtpState.TOSET.equals(smtpState)) return code == 354;
        if (SmtpState.EMAILSEND.equals(smtpState)) return code == 250;
        if (SmtpState.QUITTING.equals(smtpState)) return code == 221 || code == 250;
        return Boolean.FALSE;
    }

    public static Boolean isAErrorResponse(SmtpState smtpState, String response) {
        Integer code = getIntegerPrefix(response);
        if (code == null) return Boolean.FALSE;
        return !isASuccessResponse(smtpState, response);
    }

    private static Integer getIntegerPrefix(String str) {
        try {
            String code = str.substring(0, 3);
            return Integer.parseInt(code);
        } catch (Exception e) {
            return null;
        }

    }
}
