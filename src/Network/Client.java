package Network;

import GUI.Game;

import java.io.*;
import java.net.*;

public class Client extends Network {
    private BufferedReader inStream;
    private Writer outStream;
    private Socket client;
    private boolean connected;

    public Client(String[] args) throws IOException {
        if (client.isBound()) {
            client.close();
        }
        client = new Socket(args[0], port);
        connected = client.isConnected();
        System.out.println("Connection established");

        inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outStream = new OutputStreamWriter(client.getOutputStream());
    }

    public void sendResponse(String message) {
        if (connected) {

        }

    }

    public boolean receiveResponse() {
        try {
            String line = inStream.readLine();
            if (connected) {
                if (line.equals("done")) {

                } else if (line.equals("ready")) {

                } else if (line.startsWith("shot")) {

                } else if (line.startsWith("answer")) {

                } else if (line.equals("pass")) {

                } else if (line.startsWith("ships")) {

                } else if (line.startsWith("size")) {
                    int zahl = Integer.getInteger(line.substring(line.lastIndexOf("size") + 1));
                    Game.logicController.setGameSize(zahl);
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
