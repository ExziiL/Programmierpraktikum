package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Network {

    //region Variables
    private ServerSocket serverSocket;
    private Socket server;
    private String getMessage;
    //endregion

    /**
     * Creates an Instance of the Class Server and trys to Connect with a Client. If it established a Connection
     * the Method creates a BufferedReader and an OutputStreamWriter
     * @return true if an Object of the Class Server is created
     */
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

    /**
     * Gets the Ip-Adresse of the Server
     */
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

    protected ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * Send the necessary Data to the Client, so the Placing of the Ships can be done
     * @param size Size of the Gamefield
     * @param ships String with every Ship's Size that is used in the Game
     * @return true if an Answer "done" is received
     */
    public boolean sendInitialisation(int size, String ships) {
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
            return false;
        }
        return true;
    }

    /**
     * Send a message "ready" when all Ships are placed and wait until the other Player also send a "ready"
     */
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
