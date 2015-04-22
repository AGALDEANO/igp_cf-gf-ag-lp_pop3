package base;

import base.client.Config;
import exception.UnrespondingServerException;
import exception.WrongPortServerException;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.*;

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
    private SSLSocket securedSocket;
    private Boolean apop = Boolean.TRUE;
    private String timestamp = "";

    public Server(String hostname, int port)
            throws UnrespondingServerException {
        if (port >= 0) {
            this.port = port;

            if (hostname == null)
                throw new NullPointerException(
                        "Le nom d'hôte spécifié ne peut être nul !");
            this.hostname = hostname;

            try {
                logger.info(String.format("Getting %s's IP", hostname));
                updateAddress();
                logger.info(
                        String.format("IP is : %s", inetAddress.toString()));
            } catch (UnknownHostException e) {
                logger.error(e.toString());
                throw new UnrespondingServerException(e.toString());
            }
            try {
                logger.info(String.format("Attempting to connect to %s:%s",
                        inetAddress.toString(), port));
                updateDatagramSocket();
                logger.info(String.format("Connected to %s:%s",
                        inetAddress.toString(), port));
            } catch (SocketException e) {
                logger.fatal(e.toString());
                throw new RuntimeException(e.toString());
            } catch (IOException e) {
                logger.fatal(e.toString());
                throw new RuntimeException(e.toString());
            }
        } else
            try {
                throw new WrongPortServerException(
                        "Le port spécifié ne peut être négatif !");
            } catch (WrongPortServerException e) {
                logger.error(e.toString());
            }

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname)
            throws UnrespondingServerException {
        if (hostname == null)
            throw new NullPointerException(
                    "Le nom d'hôte spécifié ne peut être nul !");
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

    public void setPort(int port) throws UnrespondingServerException {
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
        return (Config.getSsl() ? securedSocket : socket);
    }

    public Boolean getApop() {
        return apop;
    }

    public int getTimeout() {
        return timeout;
    }

    private void updateAddress() throws UnknownHostException {
        inetAddress = InetAddress.getByName(hostname);
    }

    private void updateDatagramSocket()
            throws IOException, UnrespondingServerException {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        try {
            if (Config.getSsl()) {
                securedSocket = (SSLSocket) SSLSocketFactory.getDefault()
                        .createSocket(inetAddress, port);
                securedSocket.setEnabledCipherSuites(
                        securedSocket.getSupportedCipherSuites());
            } else
                socket = new Socket(inetAddress, port);
        } catch (ConnectException e) {
            throw new UnrespondingServerException(
                    "Il n'a pas été possible de se connecter au serveur !");
        }
    }
}