package base;

import base.client.Port;
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
        /*Pop3Client pop3Client = new Pop3Client();
        Config.setSsl(Boolean.TRUE);
        pop3Client.openConnexion(hostname[3], port[1]);
        pop3Client.signIn(user[0], user[0]);

        pop3Client.getMessage(1);
        pop3Client.closeConnexion();
        pop3Client.exit();*/
        SmtpClient smtpClient = new SmtpClient();
        smtpClient.openConnexion("localhost", Port.SMTP.getValue());
        EmailHeader emailHeader = new EmailHeader(
                Header.FROM, "test@test.fr");
        System.out.println(emailHeader.toString());
        String body = "test body";
        smtpClient.sendEmail(body,
                new EmailHeader(Header.FROM, "test@test.fr"),
                new EmailHeader(Header.TO, "testDest@test.fr"),
                new EmailHeader(Header.TO, "testDest2@test.fr"),
                new EmailHeader(Header.CC, "testDest3@test.fr"),
                new EmailHeader(Header.BCC, "testDest4@test.fr"),
                new EmailHeader(Header.BCC, "testDest5@test.fr"),
                new EmailHeader(Header.SUBJECT, "Sujet"));

    }
}


// \r\n => \n, \n => \r\n, \r\n.\r\n => \r\n.\n