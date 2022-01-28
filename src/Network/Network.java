package Network;

import Logic.DocumentWriter.DocumentWriter;
import Logic.main.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public abstract class Network {
    protected static int port = 50000;
    protected static boolean connected;
    protected static Network netplay;
    protected static boolean isServer;
    protected static Controller controller;

    private String getMessage;
    protected BufferedReader inStream;
    protected Writer outStream;
    private int[] xy = new int[3];
    private String[] shot;

    public static Network chooseNetworkTyp(boolean server) {
        if (server) {
            netplay = new Server();
            isServer = true;
        } else {
            netplay = new Client();
            isServer = false;
        }
        return netplay;
    }

    public static void setController(Controller c) {
        controller = c;
    }

  /*  public static Network chooseNetworkTyp(boolean server, Network player) {
        if (player != null) closeNetwork(player);
        if (server) {
            player = new Server();
        } else {
            player = new Client();
        }
        return player;
    }
*/

    public static boolean isServer() {
        return isServer;
    }


    protected static void closeNetwork(Network player) {
        try {
            if (player instanceof Server) {
                ((Server) player).getServerSocket().close();
            } else if (player instanceof Client) {
                ((Client) player).getClient().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean isNetworkClosed(Network player) {
        //   if (player instanceof Server) {
        //       return ((Server) player).testRead() == -1;
        //   } else if (player instanceof Client) {
        //       return ((Client) player).testRead() == -1;
        //   }

        return false;
    }

    public void sendNothing() {
        String message = "";
        try {
            outStream.write(String.format("%s%n", message));
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        String message = "save ";
        message += controller.getDocumentID();
        try {
            outStream.write(String.format("%s%n", message));
            outStream.flush();

            controller.save();

            Network.closeNetwork(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        String message = "load ";
        message += controller.getDocumentID();

        try {
            outStream.write(String.format("%s%n", message));
            outStream.flush();

            getMessage = inStream.readLine();
            if (getMessage.equals("ok")) {
                controller.loadGame();
            } else {
                load();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveSave() {
        try {
// TODO SEND OK
            getMessage = inStream.readLine();

            System.out.println(getMessage);
            if (getMessage.startsWith("save")) {
                String[] id = getMessage.split(" ");
                controller.setWriter(new DocumentWriter(id[1], true));
                controller.save();
                return id[1];
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void receiveLoad() {
        try {
            getMessage = inStream.readLine();
            System.out.println(getMessage);
            if (getMessage.startsWith("load")) {
                String[] id = getMessage.split(" ");
                controller.setWriter(new DocumentWriter(id[1], true));
                controller.loadGame();

                outStream.write(String.format("%s%n", "ok"));
                outStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Network getNetplay() {
        return netplay;
    }

    public int shoot(int x, int y) {
        try {
            System.out.println("shot " + x + " " + y);
            outStream.write(String.format("%s%n", "shot " + x + " " + y));
            outStream.flush();

            //Warten auf Message
            getMessage = inStream.readLine();
            System.out.println(getMessage);
            switch (getMessage) {
                case "answer 0":
                    return 0;
                case "answer 1":
                    return 1;
                case "answer 2":
                    return 2;
                default:
                    // wait(10);
                    shoot(x, y);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int[] receiveSaveOrShot() {
        try {
            getMessage = inStream.readLine();
            System.out.println(getMessage);
            if (getMessage.startsWith("shot")) {
                shot = getMessage.split(" ");
                xy[0] = 0;
                xy[1] = Integer.parseInt(shot[1]);
                xy[2] = Integer.parseInt(shot[2]);
            } else if (getMessage.startsWith("save")) {
                String[] id = getMessage.split(" ");
                controller.setWriter(new DocumentWriter(id[1], true));
                controller.save();
                xy[0] = 1;
                xy[1] = 99;
                xy[2] = 99;

            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return xy;
    }

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
}