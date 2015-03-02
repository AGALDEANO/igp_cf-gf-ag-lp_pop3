package util;

public class Pop3Util {
    public static String getrequestAPOP(String username, String password) {
        String request = "APOP " + username + " " + password;
        return request;

    }

    public static String getrequestRETR(int numeroMessage) {
        String request = "RETR " + numeroMessage;
        return request;
    }

    public static String getrequestQUIT() {
        String request = "QUIT";
        return request;
    }

    public static String getrequestSTAT() {
        String request = "STAT";
        return request;
    }
}
