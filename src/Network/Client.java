package Network;

import GUI.Game;

import java.io.*;
import java.net.*;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static Socket client;
    private String message;

    public boolean createClient(String ip) {
        try {
            client = new Socket(ip, port);

            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outStream = new OutputStreamWriter(client.getOutputStream());

            outStream.write("Hallo Server bin da!\n");
            outStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected Socket getClient() {
        return client;
    }

    public void receiveMessage() { //TODO Game.determenNumberofShip disable
        String[] message_split;
        try {
            message = inStream.readLine();
            if (message.startsWith("size")) {
                message_split = message.split(" ", (message.length() - "size".length()));
                System.out.println(message_split[1]);
                Game.logicController.setGameSize(Integer.parseInt(message_split[1]));
                outStream.write(String.format("%s%n", "done"));
                outStream.flush();
                receiveMessage();
            } else if (message.startsWith("ship")) {
                message_split = message.split(" ", (message.length() - "ship".length()));
                for (String x : message_split) {
                    System.out.println(x);
                }
                outStream.write(String.format("%s%n", "done"));
                outStream.flush();
            } else if (message.startsWith("ready")) {
                outStream.write(String.format("%s%n", "ready"));
                outStream.flush();
            } else {
                wait(1000);
                receiveMessage(); //TODO zeige "auf Server warten" + in Plingfield großes Label flipfloppen auf Warte und du bist dran
            }
        } catch (IOException |
                InterruptedException e) {
            e.printStackTrace();
        }
    }
}
