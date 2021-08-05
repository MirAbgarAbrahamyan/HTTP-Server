package server;

import java.io.*;
import java.net.Socket;

public class ServerTask implements Runnable {
    private final byte[] header;
    private final byte[] content;
    private final Socket connection;

    public ServerTask(Socket connection, byte[] header, byte[] content) {
        this.header = header;
        this.content = content;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            InputStream in = new BufferedInputStream(connection.getInputStream());
            StringBuilder request = new StringBuilder(80);
            while (true) {
                int c = in.read();
                if (c == '\r' || c == '\n' || c == -1) {
                    break;
                }
                request.append((char) c);
            }
            if (request.toString().contains("HTTP/") && request.toString().startsWith("GET")) {
                out.write(header);
            }
            out.write(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException ignored) {
            }
        }
    }
}
