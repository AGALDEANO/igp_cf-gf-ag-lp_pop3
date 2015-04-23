package base;

import base.client.Port;
import base.client.impl.Pop3Client;

import java.io.IOException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        /*SmtpClient smtpClient = new SmtpClient();
        try {
            ArrayList<EmailHeader> emailHeaders = new ArrayList<>();
            emailHeaders.add(new EmailHeader(Header.FROM, "ALEXANDRE"));
            emailHeaders.add(new EmailHeader(Header.TO, "laura@portable:1024"));
            emailHeaders.add(new EmailHeader(Header.TO, "corinne@localhost"));
            emailHeaders.add(new EmailHeader(Header.TO, "laura"));
            emailHeaders.add(new EmailHeader(Header.SUBJECT, "Test"));
            smtpClient.sendEmail("Salut !", emailHeaders);
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
        } catch (UnrespondingServerException e) {
            e.printStackTrace();
        }*/

        Pop3Client pop3Client = new Pop3Client();
        pop3Client.openConnexion("localhost", Port.POP3.getValue());
        pop3Client.signIn("laura");
        pop3Client.getMessage(0);
        pop3Client.getMessage(0);

    }
}