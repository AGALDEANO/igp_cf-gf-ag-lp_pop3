package util;

import base.Server;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class ServerUtil {
    private static Logger logger = Logger.getLogger(ServerUtil.class.getName());
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
            logger.fatal(e.toString());
            System.exit(-1);
        }
    }


    public static String successResponse() {
        return "+OK";
    }

    public static String errorResponse() {
        return "-ERR";
    }

    public static ServerUtil getInstance() {
        return instance;
    }

    public static void initialize(Server server) {
        instance = new ServerUtil(server);
    }

    public static String bytesToAsciiString(byte[] data) {
        try {
            return new String(data, "ascii");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString());
        }
        return null;
    }

    public Server getServer() {
        return server;
    }

    public void send(byte[] data) throws IOException {
        out.write(data);
        out.flush();
    }

    public void send(String str) throws IOException {
        send(str.getBytes());
    }

    public byte[] receive() throws IOException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        int i = in.read();
        logger.debug(String.format("data : %x", (byte) i));
        int previous = -1;
        while (i != -1 && !(i == 10 && previous == 13)) {
            bytes.add((byte) i);
            previous = i;
            i = in.read();
            logger.debug(String.format("data : %x", (byte) i));
        }
        if (i == 10 && previous == 13) {
            bytes.remove(bytes.size() - 1);
        }
        byte[] array = new byte[bytes.size()];
        for (int j = 0; j < array.length; j++) {
            array[j] = bytes.get(j);
        }
        return array;
    }

    public byte[] receiveList() throws IOException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        int i = in.read();
        logger.debug(String.format("data : %x", (byte) i));
        int previous = -1;
        Boolean endList = Boolean.FALSE;
        int indexLastCRLF = -1;
        do {
            while (i != -1 && !(i == 10 && previous == 13)) {
                bytes.add((byte) i);
                previous = i;
                i = in.read();
                logger.debug(String.format("data : %x", (byte) i));
            }
            if (i == 10 && previous == 13) {
                if (indexLastCRLF > -1 && indexLastCRLF == bytes.size() - 1 && bytes.get(indexLastCRLF) == (byte) '.') {
                    endList = Boolean.TRUE;
                } else {
                    bytes.add((byte) i);
                    indexLastCRLF = bytes.size();
                    i = in.read();
                    previous = -1;
                }
            }
        } while (!endList && i != -1);
        byte[] array = new byte[bytes.size()];
        for (int j = 0; j < array.length; j++) {
            array[j] = bytes.get(j);
        }
        return array;
    }
}
