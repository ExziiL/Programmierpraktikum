package Network;

import GUI.Game;

import java.io.*;
import java.net.*;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private boolean connected;

    public static void createClient(String ip) throws IOException{
        Socket client = new Socket(ip, port);
        System.out.println("Connection established");

        inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outStream = new OutputStreamWriter(client.getOutputStream());

    }
}
