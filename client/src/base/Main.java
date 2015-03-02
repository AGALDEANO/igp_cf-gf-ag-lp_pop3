package base;

import exceptions.UnallowedActionClientException;
import org.apache.log4j.Logger;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
    public static String[] hostname = {
            "localhost",
            "firethorn-PC"
    };
    public static int[] port = {
            110,
            3306,
            80
    };
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Client cli = new Client();
        try {
            cli.connexionAction("user:pw@" + hostname[0] + ":" + port[1]);
        } catch (UnallowedActionClientException e) {
            logger.error("fofdfgdgdf");
        }
    }
}
