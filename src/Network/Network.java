package Network;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.Socket;

public abstract class Network {
    protected static int port = 50000;
    protected static boolean connected;
    private static Network player;

    public static Network chooseNetworkTyp(boolean server) {
        closeNetwork(player);
        if (server) {
            player = new Server();
        } else {
            player = new Client();
        }
        return player;
    }


    private static void closeNetwork(Network player){
        try {
            if (player instanceof Server){
                ((Server) player).getServerSocket().close();
            }
            else if (player instanceof Client){
                ((Client) player).getClient().close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}