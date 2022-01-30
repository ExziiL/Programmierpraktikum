package Network;

import Logic.DocumentWriter.DocumentWriter;
import Logic.main.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

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
    private int checkConnected = 0;

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

    public static void closeNetwork(Network player) {
        try {
            if (player instanceof Server) {
                Socket server = ((Server) player).getServer();
                ServerSocket serverSocket = ((Server) player).getServerSocket();
                if (server != null) {
                    server.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } else if (player instanceof Client) {
                Socket client = ((Client) player).getClient();
                if (client != null) {
                    client.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Network getNetplay() {
        return netplay;
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

    public boolean load() {
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
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean receiveLoad() {
        try {
            getMessage = inStream.readLine();
            System.out.println(getMessage);
            if (getMessage.startsWith("load")) {
                String[] id = getMessage.split(" ");
                controller.setWriter(new DocumentWriter(id[1], true, true));
                controller.loadGame();

                outStream.write(String.format("%s%n", "ok"));
                outStream.flush();
                return true;
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }

    public int shoot(int x, int y) {
        try {
            System.out.println("shot " + x + " " + y);
            outStream.write(String.format("%s%n", "shot " + x + " " + y));
            outStream.flush();

            //Warten auf Message
            getMessage = inStream.readLine();
            if (getMessage != null) {
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
            } else {
                return -1;
            }

        } catch (IOException e) {
            return -1;
        }
        return -1;
    }

    public int[] receiveSaveOrShot() {
        try {
            getMessage = inStream.readLine();
            if (getMessage != null) {
                System.out.println(getMessage);
                if (getMessage.startsWith("shot")) {
                    shot = getMessage.split(" ");
                    xy[0] = 0;
                    xy[1] = Integer.parseInt(shot[1]);
                    xy[2] = Integer.parseInt(shot[2]);
                } else if (getMessage.startsWith("save")) {
                    String[] id = getMessage.split(" ");
                    controller.setWriter(new DocumentWriter(id[1], true, true));
                    controller.save();
                    xy[0] = 1;
                    xy[1] = 99;
                    xy[2] = 99;

                }
            } else {
                xy[0] = 99;
            }
        } catch (IOException e) {

            xy[0] = 99;
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