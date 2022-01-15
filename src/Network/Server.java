package Network;

import java.net.*;
import java.io.*;

public class Server extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static ServerSocket server = null;
    private static boolean connected;

    public static void createServer() throws IOException {
        if (server != null && server.isBound()) {
            server.close();
        }
        server = new ServerSocket(port);

        System.out.println("Waiting for Client to connect ...");
        Socket socket = server.accept();
        connected = socket.isConnected();
        System.out.println("Connection established");

        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new OutputStreamWriter(socket.getOutputStream());
    }

    public void sendResponse() {
        if (connected) {


        }
    }

    public void receiveClientResponse() throws IOException {
        if (connected) {
            String line = inStream.readLine();
            if (line.equals("done")) {

            } else if (line.equals("ready")) {

            } else if (line.startsWith("shot")) {

            } else if (line.startsWith("answer")) {

            } else if (line.equals("pass")) {

            }
        }
    }
}
