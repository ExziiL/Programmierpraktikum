package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Network {

    private static ServerSocket serverSocket;
    private static Socket server;
    private String getMessage;


    //public Server createServer() throws IOException {
    public boolean createServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting");
            server = serverSocket.accept();
            System.out.println("Connected");

            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
            outStream = new OutputStreamWriter(server.getOutputStream());

            String line = inStream.readLine();
            System.out.println(line);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isConnected() {
        return server.isConnected();
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

    protected Socket getServer() {
        return server;
    }


    public void sendInitialisation(int size, String ships) {
        try {
            String stringships = "";

            outStream.write(String.format("%s%n", "size " + size));
            outStream.flush();
            getMessage = inStream.readLine();
            if (!(getMessage.equals("done"))) sendInitialisation(size, ships);

            outStream.write(String.format("%s%n", "ships " + ships));
            outStream.flush();
            getMessage = inStream.readLine();
            if (!(getMessage.equals("done"))) sendInitialisation(size, ships);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendREADY() {
        try {
            outStream.write(String.format("%s%n", "ready"));
            outStream.flush();

            getMessage = inStream.readLine();
            if (!(getMessage.equals("ready"))) sendREADY();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
