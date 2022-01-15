package Network;

// import java.net.*;

import java.io.*;
import java.net.Socket;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static Socket client = null;
    private static boolean connected;

    public static void createClient(String[] args) throws IOException {
        if (client.isBound()) {
            client.close();
        }
        client = new Socket(args[0], port);
        connected = client.isConnected();
        System.out.println("Connection established");

        inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outStream = new OutputStreamWriter(client.getOutputStream());
    }

    public void sendResponse() {
        if (connected) {

        }

    }

    public void receiveServerResponse() throws IOException {
        if (connected) {
            String line = inStream.readLine();
            if (line.equals("done")) {

            } else if (line.equals("ready")) {

            } else if (line.startsWith("shot")) {

            } else if (line.startsWith("answer")) {

            } else if (line.equals("pass")) {

            } else if (line.startsWith("ships")){

            } else if (line.startsWith("size")){

            }
        }
    }
}
