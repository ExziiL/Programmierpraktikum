package Network;

import GUI.Game;

import java.io.*;
import java.net.*;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static Socket client;

    public void createClient(String ip){
        try {

            client = new Socket(ip, port);
            System.out.println("Connection established");


            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outStream = new OutputStreamWriter(client.getOutputStream());

            outStream.write(String.format("Hallo Server bin da!"));
        } catch (IOException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

    }

    protected Socket getClient(){
        return client;
    }
}
