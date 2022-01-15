package Network;

import java.io.IOException;

public class Network {
    protected static final int port = 50000;

    public static void chooseConstructor(String[] args, boolean typ) {
        try {
            if (typ) {
                Server.createServer();
            } else {
                System.out.println("not yet implemented");//Client.createClient();
            }
        } catch (IOException io){
            System.out.println(io.getMessage());
            io.printStackTrace();
        }

    }
}
