package base;

import base.client.Config;
import base.client.impl.Pop3Client;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
    private final static String[] hostname = {"localhost", "Laura_PC",
            "PC_COCO", "pop.gmail.com"};
    private final static int[] port = {110, 995};
    private final static String[] user = {"test", "laura", "corinne"};
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        Pop3Client pop3Client = new Pop3Client();
        Config.setSsl(Boolean.TRUE);
        pop3Client.openConnexion(hostname[3], port[1]);
        pop3Client.signIn(user[0], user[0]);

        pop3Client.getMessage(1);
        pop3Client.closeConnexion();
        pop3Client.exit();
        String body = "";
        body = body
                .replaceAll("\r\n", "\n")
                .replaceAll("\n", "\r\n")
                .replaceAll("\r\n.\r\n", "\r\n.\n");
    }
}


// \r\n => \n, \n => \r\n, \r\n.\r\n => \r\n.\n