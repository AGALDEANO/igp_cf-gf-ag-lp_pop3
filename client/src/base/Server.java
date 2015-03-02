package base;

import exceptions.WrongPortServerException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Server {
    private static Logger logger = Logger.getLogger(Server.class.getName());

    private InetAddress inetAddress;
    private String hostname;
    private int port = 110;
    private int timeout = 2000;
    private Socket socket;

    public Server(String hostname, int port) {
        if (port >= 0) {
            this.port = port;

            if (hostname == null) throw new NullPointerException();
            this.hostname = hostname;

            try {
                logger.info(String.format("Getting %s's IP", hostname));
                updateAddress();
                logger.info(String.format("IP is : %s", inetAddress.toString()));
            } catch (UnknownHostException e) {
                logger.error(e.toString());
                System.exit(-1);
            }
            try {
                logger.info(String.format("Attempting to connect to %s:%s", inetAddress.toString(), port));
                updateDatagramSocket();
                logger.info(String.format("Connected to %s:%s", inetAddress.toString(), port));
            } catch (SocketException e) {
                logger.fatal(e.toString());
                System.exit(-1);
            } catch (IOException e) {
                logger.fatal(e.toString());
                System.exit(-1);
            }
        } else try {
            throw new WrongPortServerException();
        } catch (WrongPortServerException e) {
            logger.error(e.toString());
        }

    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        if (hostname == null) throw new NullPointerException();
        this.hostname = hostname;
        try {
            updateAddress();
        } catch (UnknownHostException e) {
            logger.error(e.toString());
        }
        try {
            updateDatagramSocket();
        } catch (SocketException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if (port >= 0) {
            this.port = port;
            try {
                updateDatagramSocket();
            } catch (SocketException e) {
                logger.error(e.toString());
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    private void updateAddress() throws UnknownHostException {
        inetAddress = InetAddress.getByName(hostname);
    }

    private void updateDatagramSocket() throws IOException {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        socket = new Socket(inetAddress, port);
        socket.setSoTimeout(timeout);

    }
}