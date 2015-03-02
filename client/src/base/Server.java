package base;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Server {
    private InetAddress inetAddress;
    private String hostname;
    private int port = 110;
    private DatagramSocket datagramSocket;

    public Server(String hostname, int port) {
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
        try {
            updateDatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    private void updateAddress() throws UnknownHostException {
        if (inetAddress == null) inetAddress = InetAddress.getByName(hostname);
    }

    private void updateDatagramSocket() throws SocketException {
        datagramSocket = new DatagramSocket(port, inetAddress);
    }
}
