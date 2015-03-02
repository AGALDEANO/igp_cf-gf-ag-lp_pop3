package util;

import base.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class ServerUtil {
    private Server server;
    private OutputStream out;
    private InputStream in;

    public ServerUtil(Server server) {
        this.server = server;
    }

    public void send(byte[] data) throws IOException {
        server.getSocket().getOutputStream().write(data);
        server.getSocket().getOutputStream().flush();
    }

    public byte[] receive() throws IOException {

        return null;
    }
}
