package base;

import base.client.Port;
import base.client.impl.SmtpClient;
import base.email.EmailHeader;
import base.email.Header;
import exception.ErrorResponseServerException;
import exception.UnrespondingServerException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        SmtpClient smtpClient = new SmtpClient();
        try {
            smtpClient.openConnexion("laura_pc", Port.SMTP.getValue());
            /*smtpClient.sendEmail("test body",
                    new EmailHeader(Header.FROM, "test@test.fr"),
                    new EmailHeader(Header.TO, "testDest@test.fr"),
                    new EmailHeader(Header.TO, "testDest2@test.fr"),
                    new EmailHeader(Header.CC, "testDest3@test.fr"),
                    new EmailHeader(Header.BCC, "testDest4@test.fr"),
                    new EmailHeader(Header.BCC, "testDest5@test.fr"),
                    new EmailHeader(Header.SUBJECT, "Sujet"));*/
            ArrayList<EmailHeader> emailHeaders = new ArrayList<>();
            emailHeaders.add(new EmailHeader(Header.FROM, "ALEXANDRE"));
            emailHeaders.add(new EmailHeader(Header.TO, "LAURA"));
            emailHeaders.add(new EmailHeader(Header.TO, "testDest2@test.fr"));
            emailHeaders.add(new EmailHeader(Header.SUBJECT, "Test"));
            smtpClient.sendEmail("Salut !", emailHeaders);
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
        } catch (UnrespondingServerException e) {
            e.printStackTrace();
        }

    }
}