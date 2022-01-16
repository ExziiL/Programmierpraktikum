package Network;

import java.io.IOException;

public abstract class Network {
    protected final int port = 50000;
    protected static Network player;


    public void chooseConstructor(String[] args, boolean typ) {
        try {
            if (typ) {
                player = new Server();
            } else {
                //System.out.println("not yet implemented");
                player = new Client(args);
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
            io.printStackTrace();
        }
    }

    public static Network getPlayer() {
        return player;
    }
}