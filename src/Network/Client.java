package Network;

import GUI.Game;

import java.io.*;
import java.net.*;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;

    public Client(String ip) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connection established");
        connected = socket.isConnected();

        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new OutputStreamWriter(socket.getOutputStream());

        outStream.write(String.format("Hallo Server bin da!"));
    }
}
