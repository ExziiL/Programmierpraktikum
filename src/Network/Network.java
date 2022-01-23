package Network;

import java.io.IOException;

public abstract class Network {
    protected static int port = 50000;
    protected static boolean connected;
    protected static Network netplay;
    protected static boolean isServer;


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

    private static void closeNetwork(Network player) {
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

    public static Network getNetplay() {
        return netplay;
    }

    public abstract int shoot(int x, int y);

    public abstract int[] getShotAt();

    public abstract void sendAnswer(int nr);
}