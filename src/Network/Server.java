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
    public void createServer() throws IOException {

        serverSocket = new ServerSocket(port);
        server = serverSocket.accept();

        inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
        outStream = new OutputStreamWriter(server.getOutputStream());
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

    protected ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void sendInitialisation(int size, int[] ships) {
        String stringships = "";

        outStream.write("size" + size);
        outStream.flush();
        if (!(inStream.readLine().equals("done"))) sendInitialisation(size, ships);
        for (int ship: ships) {
            stringships = stringships + ship;
        }
        outStream.write("ships" + stringships);
        if (!(inStream.readLine().equals("done"))) sendInitialisation(size, ships);

    }
}
