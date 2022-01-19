package Network;

import java.net.*;
import java.io.*;
import java.util.Enumeration;

public class Server extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static ServerSocket serverSocket;
    private static boolean connected;

    //public Server createServer() throws IOException {
    public Server() throws IOException {

        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for Connection ...");
        Socket socket = serverSocket.accept();
        System.out.println("Connection established");

        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new OutputStreamWriter(socket.getOutputStream());

    }

    public String getIp() {
        return serverSocket.getInetAddress().getHostAddress();
    }
}
