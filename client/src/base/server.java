package base;

import java.net.*;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class server {
    private InetAddress inetAddress;
    private String hostname;
    private int port = 110;
    private SocketAddress socketAddress;
    private DatagramSocket datagramSocket;

    public server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
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
            e.printStackTrace();
        }
        updateSocketAddress();
        updateDatagramSocket();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if (port >= 0) {
            this.port = port;
            updateDatagramSocket();
        }
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    private void updateAddress() throws UnknownHostException {
        if (inetAddress == null) inetAddress = InetAddress.getByName(hostname);
    }

    private void updateDatagramSocket() {

    }

    private void updateSocketAddress() {
        socketAddress = new InetSocketAddress(inetAddress, port);
    }
}
