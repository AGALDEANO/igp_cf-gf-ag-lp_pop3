package util;

import base.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class ServerUtil {
    private static ServerUtil instance;
    private Server server;
    private OutputStream out;
    private InputStream in;

    private ServerUtil(Server server) {
        this.server = server;
        try {
            this.out = server.getSocket().getOutputStream();
            this.in = server.getSocket().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String successResponse() {
        return "+OK";
    }

    public static String errorResponse() {
        return "+ERR";
    }

    public static ServerUtil getInstance(Server server) {
        if (instance == null) {
            instance = new ServerUtil(server);
        }
        return instance;
    }

    public static String bytesToAsciiString(byte[] data) {
        try {
            return new String(data, "ascii");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(byte[] data) throws IOException {
        server.getSocket().getOutputStream().write(data);
        server.getSocket().getOutputStream().flush();
    }

    public void send(String str) throws IOException {
        send(str.getBytes());
    }

    public byte[] receive() throws IOException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        int i;
        i = in.read();
        while (i != -1) {
            bytes.add((byte) i);
        }
        byte[] array = new byte[bytes.size()];
        for (int j = 0; j < array.length; j++) {
            array[j] = bytes.get(j);
        }
        return array;
    }
    
}
