package base.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 10/03/2015.
 */
public class EmailUtil {
    private static String extension = ".email";
    private static String path = "emails";

	public static void saveEmail(Email email, String username)
			throws IOException {
		String filepath = path + "/" + username + "/" + email.getId() + extension;
        File file = new File(filepath);
		if (!file.getParentFile().mkdirs())
			throw new IOException("Directory hasn't been created.");
		try {
			FileWriter writer = new FileWriter(filepath);
			writer.write(email.toString());
			writer.close();
		} catch (IOException e) {
			throw e;
		}
	}

    public static ArrayList<Email> getEmails(String username) throws IOException {
        String filepath;
		StringBuilder data = new StringBuilder();
		int n;
        ArrayList<Email> emailArrayList = new ArrayList<>();
        File f = new File(path + "/" + username);
		if (f != null && f.exists() && f.isDirectory()) {
			for (String filename : f.list()) {
                if (filename.endsWith(extension)) {
                    filepath = path + "/" + username + "/" + filename;
                    FileInputStream in = new FileInputStream(filepath);
                    n = in.read();
                    while (n != -1) {
						data.append((char) n);
						n = in.read();
                    }
					emailArrayList.add(new Email(data.toString(),
							Long.parseLong(filename.substring(0,
									filename.lastIndexOf(extension)))));
					in.close();
				}
            }
        }
        return emailArrayList;
    }
}
