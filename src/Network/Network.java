package Network;

import Logic.main.Controller;

import java.io.IOException;

public abstract class Network {
    protected static int port = 50000;
    protected static boolean connected;
    protected static Network netplay;
    protected static boolean isServer;
    protected static Controller controller;


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
        if (player instanceof Server) {
            return ((Server) player).testRead() == -1;
        } else if (player instanceof Client) {
            return ((Client) player).testRead() == -1;
        }

        return true;
    }

    public abstract void sendNothing();

    public abstract void save();

    public abstract void load();

    public abstract String receiveSave();

    public abstract void receiveLoad();

    public static Network getNetplay() {
        return netplay;
    }

    public abstract int shoot(int x, int y);

    public abstract int[] receiveSaveOrShot();

    public abstract void sendAnswer(int nr);
}