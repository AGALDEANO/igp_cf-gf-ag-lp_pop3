package base;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class server {
    private InetAddress address;
    private String hostname;
    private int port = 110;
    private DatagramSocket socket;

    public server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    private void updateAddress() {

    }
}
