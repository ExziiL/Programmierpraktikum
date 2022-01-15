package Network;

import java.net.*;
import java.io.*;

public class Server extends Network {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(port);

        System.out.println("Waiting for Client to connect ...");
        Socket socket = server.accept();
        System.out.println("Connection established");

        BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Writer outStream =  new OutputStreamWriter(socket.getOutputStream());




    }
}
