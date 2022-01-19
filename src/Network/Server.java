package Network;

import java.net.*;
import java.io.*;
import java.util.Enumeration;

public class Server extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static ServerSocket serverSocket;
    private static Socket server;

    //public Server createServer() throws IOException {
    public void createServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for Connection ...");
            server = serverSocket.accept();
            System.out.println("Connection established");

            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
            outStream = new OutputStreamWriter(server.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public String getIp() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }

    protected ServerSocket getServerSocket(){
        return serverSocket;
    }
}
