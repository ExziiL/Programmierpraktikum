package Network;

import GUI.Game;

import java.net.*;
import java.io.*;
import java.util.Enumeration;

public class Server extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static ServerSocket serverSocket;
    private static Socket server;
    private String get_Message;

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
        } catch (IOException e){
            e.printStackTrace();
            return false;
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

    protected ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void sendInitialisation(int size, int[] ships) {
        try {
            String stringships = "";

            outStream.write(String.format("%s%n","size " + size));
            outStream.flush();
            get_Message = inStream.readLine();
            if (!(get_Message.equals("done"))) sendInitialisation(size, ships);

            for (int ship : ships) {
                stringships = stringships + ship;
            }
            outStream.write(String.format("%s%n","ships " + stringships));
            outStream.flush();
            get_Message = inStream.readLine();
            if (!(get_Message.equals("done"))) sendInitialisation(size, ships);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void sendREADY(){
        try {
            outStream.write(String.format("%s%n","ready"));
            outStream.flush();

            get_Message = inStream.readLine();
            if (!(get_Message.equals("ready"))) sendREADY();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
