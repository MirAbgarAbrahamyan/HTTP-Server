import helper.Utility;
import server.HttpServer;

import java.io.UnsupportedEncodingException;

/**
 *         args[0]    args[1]  args[2]
 * [rightmost number] [port] [encoding]
 */

public class ServerStarter {
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > (1 << 16) - 1) {
                port = 80;
            }
        } catch (RuntimeException e) {
            port = 80; // default HTTP port number
        }

        String encoding = "US-ASCII";
        if (args.length > 2) {
            encoding = args[2];
        }

        int rightMostNumber;
        try {
            rightMostNumber = Integer.parseInt(args[0]);
            if (rightMostNumber < 2) {
                rightMostNumber = 10;
            }
        } catch (RuntimeException e) {
            rightMostNumber = 10;
        }

        try {
            byte[] data = ("Here will be some data for example I take random numbers between [1, " +
                    rightMostNumber + "] range\n"
                    + Utility.randomInteger(1, rightMostNumber) + "\n").getBytes(encoding);
            HttpServer server = new HttpServer(port, data, encoding, "text/plain");
            server.start();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
