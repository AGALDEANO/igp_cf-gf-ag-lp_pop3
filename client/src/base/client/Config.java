package base.client;

import org.apache.log4j.Logger;

/**
 * @author Alexandre
 *         17/03/2015
 */
public class Config {
	private static int defaultPort = 110;
	private static int defaultSSLPort = 995;
	private static Logger logger = Logger.getLogger(Config.class.getName());

	public static int getDefaultPort() {
		return defaultPort;
	}

	public static int getDefaultSSLPort() {
		return defaultSSLPort;
	}
}
