package Network;

import java.io.IOException;

public class Network {
    protected static int port = 50000;

    public static void chooseNetworkTyp(boolean server, String ip) {
        try {
            if (server) {
                Server.createServer();
            } else {
                Client.createClient(ip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}