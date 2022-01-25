package Network;

import java.io.*;
import java.net.Socket;

public class Client extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static Socket client;
    private String message;
    private int[] xy = new int[2];
    private String[] shot;

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
                controller.setGameSize(Integer.parseInt(message_split[1]));
                outStream.write(String.format("%s%n", "done"));
                outStream.flush();
                receiveMessage();
            } else if (message.startsWith("ship")) {
                message_split = message.split(" ", (message.length() - "ship".length()));
                setShipsinGame(message_split);
                outStream.write(String.format("%s%n", "done"));
                outStream.flush();
            } else if (message.startsWith("ready")) {
                outStream.write(String.format("%s%n", "ready"));
                outStream.flush();
            } else {
                wait(100);
                receiveMessage(); //TODO zeige "auf Server warten" + in Plingfield großes Label flipfloppen auf Warte und du bist dran
            }
        } catch (IOException |
                InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int shoot(int x, int y) {
        System.out.println("shoot " + x + " " + y);
        try {
            outStream.write(String.format("%s%n", "shoot " + x + " " + y));
            outStream.flush();

            message = inStream.readLine();
            if (message.equals("answer 0")) {
                return 0;
            } else if (message.equals("answer 1")) {
                return 1;
            } else if (message.equals("answer 2")) {
                return 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int[] getShotAt() {
        message = null;
        try {
            // Wartet auf Message
            message = inStream.readLine();

            System.out.println(message);
            if (message.startsWith("shoot")) {
                shot = message.split(" ");
                xy[0] = Integer.parseInt(shot[1]);
                xy[1] = Integer.parseInt(shot[2]);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return xy;
    }

    @Override
    public void sendAnswer(int nr) {
        String message = "answer ";
        System.out.println("answer " + nr);
        try {
            outStream.write(String.format("%s%n", message + nr));
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        controller.setDestroyedThreeShips(two);
        controller.setDestroyedFourShips(four);
        controller.setDestroyedFiveShips(five);


    }
}
