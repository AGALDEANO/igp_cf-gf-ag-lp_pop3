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
	private static String inbox = "inbox";
	private static String outbox = "outbox";

	public static String getPath() {
		return path;
	}

	public static String getInbox() {
		return inbox;
	}

	public static String getOutbox() {
		return outbox;
	}

	public static void saveReceivedEmail(Email email, String username)
			throws IOException {
		String filepath =
				path + "/" + username + "/" + inbox + "/" + email.getId() + extension;
		File file = new File(filepath);
		if (!file.getParentFile().mkdirs())
			throw new IOException("Directory hasn't been created.");
		FileWriter writer = null;
		try {
			writer = new FileWriter(filepath);
			writer.write(email.toString());
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public static void saveSentEmail(Email email, String username)
			throws IOException {
		String filepath =
				path + "/" + username + "/" + outbox + "/" + email.getId() + extension;
		File file = new File(filepath);
		if (!file.getParentFile().mkdirs())
			throw new IOException("Directory hasn't been created.");
		FileWriter writer = null;
		try {
			writer = new FileWriter(filepath);
			writer.write(email.toString());
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public static ArrayList<Email> getSentEmails(String username)
			throws IOException {
		String filepath;
		StringBuilder data;
		int n;
		ArrayList<Email> emailArrayList = new ArrayList<>();
		File f = new File(path + "/" + username + "/" + EmailUtil.getOutbox());
		if (f.exists() && f.isDirectory()) {
			String[] list = f.list();
			if (list != null)
				return emailArrayList;
			for (String filename : f.list()) {
				if (filename.endsWith(extension)) {
					data = new StringBuilder();
					filepath = path + "/" + username + "/" + filename;
					FileInputStream in = null;
					try {
						in = new FileInputStream(filepath);
						n = in.read();
						while (n != -1) {
							data.append((char) n);
							n = in.read();
						}
						emailArrayList.add(new Email(data.toString(),
								Long.parseLong(filename.substring(0,
										filename.lastIndexOf(extension)))));
					} catch (IOException e) {
						throw e;
					} finally {
						if (in != null)
							in.close();
						return emailArrayList;
					}

				}
			}
		}
		return emailArrayList;
	}

	public static ArrayList<Email> getReceivedEmails(String username)
			throws IOException {
		String filepath;
		StringBuilder data;
		int n;
		ArrayList<Email> emailArrayList = new ArrayList<>();
		File f = new File(path + "/" + username + "/" + EmailUtil.getInbox());
		if (f.exists() && f.isDirectory()) {
			String[] list = f.list();
			if (list != null)
				return emailArrayList;
			for (String filename : f.list()) {
				if (filename.endsWith(extension)) {
					data = new StringBuilder();
					filepath = path + "/" + username + "/" + filename;
					FileInputStream in = null;
					try {
						in = new FileInputStream(filepath);
						n = in.read();
						while (n != -1) {
							data.append((char) n);
							n = in.read();
						}
						emailArrayList.add(new Email(data.toString(),
								Long.parseLong(filename.substring(0,
										filename.lastIndexOf(extension)))));
					} catch (IOException e) {
						throw e;
					} finally {
						if (in != null)
							in.close();
						return emailArrayList;
					}

				}
			}
		}
		return emailArrayList;
	}

	public static String emailHeadersToString(EmailHeader... headers) {
		StringBuilder str = new StringBuilder("");
		for (EmailHeader header : headers) {
			str.append(header.toString());
		}
		str.append(Email.getEndLine());
		return str.toString();
	}

	public static String emailBodyHeadersToString(String body, EmailHeader... headers) {
		return emailHeadersToString(headers) + body + Email.getEndFile();
	}

	public static boolean validEmailAddress(String address) {
		return address.split("@").length == 2;
	}
}
