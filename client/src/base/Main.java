package base;

import base.client.Config;
import base.client.impl.Pop3Client;
import base.client.impl.SmtpClient;
import base.email.EmailHeader;
import base.email.Header;

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
        SmtpClient SmtpClient = new SmtpClient();
        SmtpClient.openConnexion(hostname[3], port[1]);
        String body = "";
        body = body
                .replaceAll("\r\n", "\n")
                .replaceAll("\n", "\r\n")
                .replaceAll("\r\n.\r\n", "\r\n.\n");
        SmtpClient.sendEmail(body, new EmailHeader(Header.FROM,"gael.ferjaniblabla"));
    }
}


// \r\n => \n, \n => \r\n, \r\n.\r\n => \r\n.\n