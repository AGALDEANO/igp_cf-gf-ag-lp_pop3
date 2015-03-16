package util;

import base.Server;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class ServerUtil {
    private static Logger logger = Logger.getLogger(ServerUtil.class.getName());
    private static ServerUtil instance;
    private static String endLine = "\r\n";
    private static String endFile = "\r\n.\r\n";
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
			throw new RuntimeException(e.toString());
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

    public static String bytesToUTF8String(byte[] data) {
        try {
            return new String(data, "utf-8");
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
		send(str.getBytes("ascii"));
	}

    public byte[] receive() throws IOException, UnrespondingServerException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        bytes = read(bytes, 3);
        byte[] array = new byte[bytes.size()];
        for (int j = 0; j < array.length; j++) {
            array[j] = bytes.get(j);
        }
        return array;
    }

    private ArrayList<Byte> read(ArrayList<Byte> bytes, int n) throws IOException, UnrespondingServerException {
        if (n < 1) {
            throw new UnrespondingServerException("Malgré plusieurs tentatives, le serveur n'a pas répondu.");
        }
        try {
            int i = in.read();
            logger.trace(String.format("data : %x", (byte) i));
            int size;
            Boolean endLoop = Boolean.FALSE;
            while (i != -1 && !endLoop) {
                bytes.add((byte) i);
                i = in.read();
                logger.trace(String.format("data : %x", (byte) i));
                size = bytes.size();
                endLoop = size > 1 &&
                        i == '\n' &&
                        bytes.get(size - 1) == '\r';
            }
            if (i != -1) {
                bytes.add((byte) i);
            }
            return bytes;
        } catch (SocketTimeoutException e) {
            logger.warn("Nouvelle tentative...");
            return read(bytes, n - 1);
        } catch (SocketException e) {
            throw new UnrespondingServerException("Le serveur s'est déconnecté.");
        }
    }

    private ArrayList<Byte> readList(ArrayList<Byte> bytes, int n) throws IOException, UnrespondingServerException {
        if (n < 1) {
            throw new UnrespondingServerException("Malgré plusieurs tentatives, le serveur n'a pas répondu.");
        }
        try {
            int i = in.read();
            logger.trace(String.format("data : %x", (byte) i));
            int size;
            Boolean endLoop = Boolean.FALSE;
            while (i != -1 && !endLoop) {
                bytes.add((byte) i);
                i = in.read();
                logger.trace(String.format("data : %x", (byte) i));
                size = bytes.size();
                if (size > 4) {
                    if (bytes.get(0) == '-' &&
                            bytes.get(1) == 'E' &&
                            bytes.get(2) == 'R' &&
                            bytes.get(3) == 'R') {
                        endLoop = i == '\n' &&
                                bytes.get(size - 1) == '\r';
                    } else {
                        endLoop = i == '\n' &&
                                bytes.get(size - 1) == '\r' &&
                                bytes.get(size - 2) == '.' &&
                                bytes.get(size - 3) == '\n' &&
                                bytes.get(size - 4) == '\r';
                    }
                } else {
                    endLoop = Boolean.FALSE;
                }

            }
            if (i != -1) {
                bytes.add((byte) i);
            }
            return bytes;
        } catch (SocketTimeoutException e) {
            logger.warn("Nouvelle tentative...");
            return readList(bytes, n - 1);
        } catch (SocketException e) {
            throw new UnrespondingServerException("Le serveur s'est déconnecté.");
        }
    }

    public byte[] receiveList() throws IOException, UnrespondingServerException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        bytes = readList(bytes, 3);
        byte[] array = new byte[bytes.size()];
        for (int j = 0; j < array.length; j++) {
            array[j] = bytes.get(j);
        }
        return array;
    }
}
