package Network;

import Logic.main.LogicConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client extends Network {

    //region Variables
    private Socket client;
    private String getMessage;
    //endregion

    /**
     * "client" trys to connect to a Server. If a Connection is established a BufferedReader
     * and OutputStreamWriter are created
     * @param ip IP-Adresse of the Server to that the Client wants to connect
     * @return true if the client is connected
     */
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

    public Socket getClient() {
        return client;
    }

    /**
     * When the Client receives a message, this message is split into every word of it and handled accordingly.
     * So if the message start with size, the GameSize is set, the EnemyGame is created
     * and the GameMode in EnemyGame is set to Online. Then it writes to the Server a "done"-message
     * If the message starts with "ships", the Array without "ship" is in setShipsinGame translated into
     * a total number of each Ship-kind. Then it send a "done" to the Server.
     * IF the message is "ready" an Answer with also "ready" is sent.
     * @return true if an Answer is sent
     */
    public boolean receiveMessage() {
        String[] message_split;
        try {
            getMessage = inStream.readLine();
            if (getMessage.startsWith("size")) {
                message_split = getMessage.split(" ", (getMessage.length() - "size".length()));
                System.out.println(message_split[1]);
                controller.setGameSize(Integer.parseInt(message_split[1]));
                controller.createEnemyGame(Integer.parseInt(message_split[1]));
                controller.setEnemyGameGameMode(LogicConstants.GameMode.ONLINE);
                outStream.write(String.format("%s%n", "done"));
                outStream.flush();
                receiveMessage();
            } else if (getMessage.startsWith("ship")) {
                message_split = getMessage.split(" ", (getMessage.length() - "ship".length()));
                setShipsinGame(message_split);
                outStream.write(String.format("%s%n", "done"));
                outStream.flush();
            } else if (getMessage.startsWith("ready")) {
                outStream.write(String.format("%s%n", "ready"));
                outStream.flush();
            } else {
                wait(100);
                receiveMessage();
            }
        } catch (IOException |
                InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * Parses the String numbers to an Intereger, so that count of each Ship-kind can be created
     * @param message_split Array with the Size of each Ship used in the Game as String
     */
    private void setShipsinGame(String[] message_split) {
        int two = 0, three = 0, four = 0, five = 0;

        for (String x : message_split) {
            try {
                int shipSize = Integer.parseInt(x);
                switch (shipSize) {
                    case 2:
                        two++;
                        break;
                    case 3:
                        three++;
                        break;
                    case 4:
                        four++;
                        break;
                    case 5:
                        five++;
                        break;
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        controller.setAllTwoShips(two);
        controller.setAllThreeShips(three);
        controller.setAllFourShips(four);
        controller.setAllFiveShips(five);

        controller.setDestroyedTwoShips(two);
        controller.setDestroyedThreeShips(three);
        controller.setDestroyedFourShips(four);
        controller.setDestroyedFiveShips(five);

    }
}
