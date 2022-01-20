package Network;

import GUI.Game;

import java.io.*;
import java.net.*;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static Socket client;

    public void createClient(String ip) throws IOException {

        client = new Socket(ip, port);

        inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outStream = new OutputStreamWriter(client.getOutputStream());

        outStream.write(String.format("Hallo Server bin da!"));
    }

    protected Socket getClient() {
        return client;
    }
}
