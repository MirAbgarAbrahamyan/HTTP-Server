package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private final byte[] header;
    private final byte[] content;
    private final String encoding;

    public HttpServer(int port, byte[] data, String encoding, String mimeType) {
        this.port = port;
        this.content = data;
        this.encoding = encoding;
        String header = "HTTP/1.1 200 OK\r\n" +
                "Server: Abgar's-HTTP-Server\r\n" +
                "Content-length: " + content.length + "\r\n" +
                "Content-type: " + mimeType + "; charset=" + this.encoding + "\r\n\r\n";
        this.header = header.getBytes(StandardCharsets.US_ASCII);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            ExecutorService pool = Executors.newFixedThreadPool(100);
            while (true) {
                Socket clientConnection = server.accept();
                System.out.println("Someone is connected");
                ServerTask task = new ServerTask(clientConnection, header, content);
                pool.submit(task);
            }
        } catch (IOException e) {
            System.err.println("Cannot start the server");
        }
    }
}
