package base;

import exceptions.WrongPortServerException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Server {
    private InetAddress inetAddress;
    private String hostname;
    private int port = 110;
    private Socket socket;

    public Server(String hostname, int port) {
        if (port >= 0) {
            this.port = port;

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
        } else try {
            throw new WrongPortServerException();
        } catch (WrongPortServerException e) {
            e.printStackTrace();
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

    public Socket getSocket() {
        return socket;
    }

    private void updateAddress() throws UnknownHostException {
        inetAddress = InetAddress.getByName(hostname);
    }

    private void updateDatagramSocket() throws SocketException {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket = new Socket(inetAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}