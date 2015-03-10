package base.email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 10/03/2015.
 */
public class EmailUtil {
    private static String extension = ".mail";

    public static void saveEmail(Email email, String username) throws IOException {
        File f = new File(username);
        String filename = username + "/" + email.getId() + extension;
        if (f.exists() && f.isDirectory()) {
            File mail = new File(filename);
            if (mail.exists() && mail.delete() && mail.createNewFile()) {
                FileOutputStream out = new FileOutputStream(filename);
                out.write(email.toString().getBytes());
                out.close();
            }
        }
    }

    private static ArrayList<Email> getEmails(String username) throws IOException {
        File f = new File(username);
        if (f.exists() && f.isDirectory()) {

        }
        return null;
    }
}
