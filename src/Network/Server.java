package Network;

import java.net.*;
import java.io.*;
import java.util.Enumeration;

public class Server extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static ServerSocket serverSocket;

    //public Server createServer() throws IOException {
    public Server() throws IOException {
        if (connected) {
            socket.close();
        }

        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for Connection ...");
        socket = serverSocket.accept();
        System.out.println("Connection established");
        connected = socket.isConnected();

        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new OutputStreamWriter(socket.getOutputStream());

    }

    public String getIp() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch (IOException e){
            e.printStackTrace();
        }
        return ip;
    }
}
