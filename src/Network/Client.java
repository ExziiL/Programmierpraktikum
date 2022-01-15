package Network;

// import java.net.*;
import java.io.*;
import java.net.Socket;

public class Client extends Network {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket(args[0], port);
        System.out.println("Connection established");

        BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Writer outStream = new OutputStreamWriter(client.getOutputStream());


    }
}
