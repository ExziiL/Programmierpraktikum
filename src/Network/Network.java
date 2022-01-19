package Network;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public abstract class Network {
    protected static int port = 50000;

    public static Network chooseNetworkTyp(boolean server, String ip) {
        Network player = null;
        try {
            if (server) {
                player = new Server();
            } else {
                player = new Client(ip);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    //public abstract String getIp();
}