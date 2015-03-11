package base;

import base.client.Client;
import base.client.ClientThrowable;
import base.email.Email;
import base.email.EmailUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
    public static String[] hostname = {
            "localhost",
            "Laura_PC",
            "PC_COCO"
    };
    public static int[] port = {
            110
    };
    public static String[] user = {
            "test",
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
        client.getMessage(3);
        waitForAnswer(client);
        System.out.println(client.getMessage().headersToString());
        client.closeConnexion();
        waitForAnswer(client);
        client.exit();
        try {
            ArrayList<Email> emails = EmailUtil.getEmails("test");
            emails.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitForAnswer(Client client) {
        String success, error;
        do {
            success = client.getSucessMessage();
            error = client.getErrorMessage();
        } while (success == null && error == null);
    }
}
