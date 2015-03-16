package base;

import base.client.Client;
import base.client.ClientThrowable;
import org.apache.log4j.Logger;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
	protected final static String[] hostname = { "localhost",
            "Laura_PC",
            "PC_COCO"
    };
	protected final static int[] port = { 110
    };
	protected final static String[] user = { "test",
            "laura",
            "corinne"
    };
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ClientThrowable client = new ClientThrowable();
        client.start();
        client.openConnexion(hostname[2], port[0]);
        waitForAnswer(client);
        client.signIn(user[0]);
        waitForAnswer(client);
        client.getMessage(4);
        waitForAnswer(client);
        System.out.println(client.getMessage().headersToString());
        client.closeConnexion();
        waitForAnswer(client);
        client.exit();
    }

    public static void waitForAnswer(Client client) {
        String success, error;
        do {
            success = client.getSucessMessage();
            error = client.getErrorMessage();
        } while (success == null && error == null);
    }
}
