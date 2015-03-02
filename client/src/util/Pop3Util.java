package util;

import org.apache.log4j.Logger;

public class Pop3Util {
    private static Logger logger = Logger.getLogger(Pop3Util.class.getName());
    public static String getrequestAPOP(String username, String password) {
        String request = "APOP " + username + " " + password;
        logger.debug(String.format("Request : %s", request));
        return request;

    }

    public static String getrequestRETR(int numeroMessage) {
        String request = "RETR " + numeroMessage;
        logger.debug(String.format("Request : %s", request));
        return request;
    }

    public static String getrequestQUIT() {
        String request = "QUIT";
        logger.debug(String.format("Request : %s", request));
        return request;
    }

    public static String getrequestSTAT() {
        String request = "STAT";
        logger.debug(String.format("Request : %s", request));
        return request;
    }
}
