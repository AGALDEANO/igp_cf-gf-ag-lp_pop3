package base;

import base.client.Client;
import base.email.Email;
import base.email.EmailUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
	private final static String[] hostname = { "localhost", "Laura_PC",
            "PC_COCO"
    };
	private final static int[] port = { 110 };
	private final static String[] user = { "test", "laura",
            "corinne"
    };
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
		Client client = new Client();
        client.openConnexion(hostname[2], port[0]);
        client.signIn(user[0]);
        client.getMessage(4);
        System.out.println(client.getMessage().headersToString());
        client.closeConnexion();
        client.exit();
        try {
            ArrayList<Email> emails = EmailUtil.getEmails("test");
            emails.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
