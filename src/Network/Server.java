package Network;

import Logic.DocumentWriter.DocumentWriter;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Network {
    private static BufferedReader inStream;
    private static Writer outStream;
    private static ServerSocket serverSocket;
    private static Socket server;
    private String get_Message;
    private int[] xy = new int[3];
    private String[] shot;

    //public Server createServer() throws IOException {
    public boolean createServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting");
            server = serverSocket.accept();
            System.out.println("Connected");

            inStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
            outStream = new OutputStreamWriter(server.getOutputStream());

            String line = inStream.readLine();
            System.out.println(line);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getIp() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }

    protected ServerSocket getServerSocket() {
        return serverSocket;
    }


    public void sendInitialisation(int size, String ships) {
        try {
            String stringships = "";

            outStream.write(String.format("%s%n", "size " + size));
            outStream.flush();
            get_Message = inStream.readLine();
            if (!(get_Message.equals("done"))) sendInitialisation(size, ships);

            outStream.write(String.format("%s%n", "ships " + ships));
            outStream.flush();
            get_Message = inStream.readLine();
            if (!(get_Message.equals("done"))) sendInitialisation(size, ships);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendREADY() {
        try {
            outStream.write(String.format("%s%n", "ready"));
            outStream.flush();

            get_Message = inStream.readLine();
            if (!(get_Message.equals("ready"))) sendREADY();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int shoot(int x, int y) {
        try {
            System.out.println("shoot " + x + " " + y);
            outStream.write(String.format("%s%n", "shoot " + x + " " + y));
            outStream.flush();

            //Warten auf Message
            get_Message = inStream.readLine();
            System.out.println(get_Message);
            switch (get_Message) {
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

    @Override
    public int[] receiveSaveOrShot() {
        try {
            get_Message = inStream.readLine();
            System.out.println(get_Message);
            if (get_Message.startsWith("shoot")) {
                shot = get_Message.split(" ");
                xy[0] = 0;
                xy[1] = Integer.parseInt(shot[1]);
                xy[2] = Integer.parseInt(shot[2]);
            } else if (get_Message.startsWith("save")) {
                String[] id = get_Message.split(" ");
                controller.setWriter(new DocumentWriter(id[1]));
                controller.save(false);
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

    public void save() {
        String message = "save ";
        message += controller.getDocumentID();
        try {
            outStream.write(String.format("%s%n", message));
            outStream.flush();

            controller.save(true);

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

            controller.loadGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String receiveSave() {
        try {

            get_Message = inStream.readLine();

            System.out.println(get_Message);
            if (get_Message.startsWith("save")) {
                String[] id = get_Message.split(" ");
                controller.setWriter(new DocumentWriter(id[1]));
                controller.save(false);
                return id[1];
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void receiveLoad() {
        try {
            get_Message = inStream.readLine();
            System.out.println(get_Message);
            if (get_Message.startsWith("load")) {
                String[] id = get_Message.split(" ");
                controller.setWriter(new DocumentWriter(id[1]));
                controller.loadGame();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNothing() {
        String message = "";
        try {
            outStream.write(String.format("%s%n", message));
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int testRead() {

        // try {
        //     return inStream.ready();
        // } catch (IOException e) {
        //     return -1;
        // }
        return 0;
    }
}
