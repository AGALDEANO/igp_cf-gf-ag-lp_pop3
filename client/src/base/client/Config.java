package base.client;

import org.apache.log4j.Logger;

/**
 * @author Alexandre
 *         17/03/2015
 */
public class Config {
	private static Boolean autosave = Boolean.TRUE;
	private static Boolean ssl = Boolean.FALSE;
	private static Boolean apop = Boolean.TRUE;
	private static Logger logger = Logger.getLogger(Config.class.getName());

	public static Boolean getAutosave() {
		return autosave;
	}

	public static void setAutosave(Boolean autosave) {
		Config.autosave = autosave;
	}

	public static Boolean getApop() {
		return apop;
	}

	public static void setApop(Boolean apop) {
		Config.apop = apop;
	}

	public static Boolean getSsl() {
		return ssl;
	}

	public static void setSsl(Boolean ssl) {
		Config.ssl = ssl;
	}
}
