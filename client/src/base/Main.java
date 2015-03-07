package base;

import org.apache.log4j.Logger;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
    public static String[] hostname = {
            "localhost"
    };
    public static int[] port = {
            110
    };
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ClientThrowable client = new ClientThrowable();
        client.start();
        client.openConnexion(hostname[0], port[0]);
        waitForAnswer(client);
        client.enterLogin("admin");
        waitForAnswer(client);
        client.enterPassword("pw");
        waitForAnswer(client);
        client.getStat();
        waitForAnswer(client);
        client.getMessage(1);
        waitForAnswer(client);
        client.signIn("ffd");
        waitForAnswer(client);
        client.getMessageList();
        waitForAnswer(client);
        client.getMessageDetails(1);
        waitForAnswer(client);
        client.closeConnexion();
        waitForAnswer(client);
    }

    public static void waitForAnswer(Client client) {
        String success, error;
        do {
            success = client.getSucessMessage();
            error = client.getErrorMessage();
            if (success != null) {
                System.out.println(success);
            }
            if (error != null) {
                System.out.println(error);
            }
        } while (success == null && error == null);
    }
}
