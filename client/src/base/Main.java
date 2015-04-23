package base;

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
            ArrayList<EmailHeader> emailHeaders = new ArrayList<>();
            emailHeaders.add(new EmailHeader(Header.FROM, "ALEXANDRE"));
            emailHeaders.add(new EmailHeader(Header.TO, "laura@EPULWKS42:1024"));
            emailHeaders.add(new EmailHeader(Header.TO, "corinne@EPULWKS48:1024"));
            emailHeaders.add(new EmailHeader(Header.SUBJECT, "Message pour Corinne et Laura"));
            smtpClient.sendEmail("Test de message !", emailHeaders);
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
        } catch (UnrespondingServerException e) {
            e.printStackTrace();
        }

    }
}