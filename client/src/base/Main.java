package base;

import base.client.Client;
import base.client.Config;
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
			"PC_COCO", "pop.gmail.com" };
	private final static int[] port = { 110, 995 };
	private final static String[] user = { "test", "laura", "corinne" };
	private static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		Config.setSsl(Boolean.TRUE);
		client.openConnexion(hostname[3], port[1]);
		client.signIn(user[0], user[0]);

		client.getMessage(1);
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
