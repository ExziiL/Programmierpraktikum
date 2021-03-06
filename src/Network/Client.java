package Network;

import Logic.main.LogicConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client extends Network {
    private Socket client;
    private String getMessage;

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
                receiveMessage(); //TODO zeige "auf Server warten" + in Plingfield gro??es Label flipfloppen auf Warte und du bist dran
            }
        } catch (IOException |
                InterruptedException e) {
            return false;
        }
        return true;
    }


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
