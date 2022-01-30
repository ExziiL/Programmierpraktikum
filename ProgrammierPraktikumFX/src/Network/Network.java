package Network;

import Logic.DocumentWriter.DocumentWriter;
import Logic.main.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Network {

    //region Variables
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
    //endregion

    /**
     * Chooses between a Server and Client, wehen called
     *
     * @param server indicates if The User is the Server or the Client
     * @return an Object of a Server or Client
     */
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

    /**
     * Closes the Connection if the end of a Game is reached or a Player has quit/ saved the Game
     *
     * @param player Instance of Object of Type Network
     */
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

    /**
     * Send to the other OnlinePlayer the massage that the User is saving and leaving the Game
     */
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

    /**
     * Send if the User loads a File of an OnlineGame, so that the other Player can load the same
     * File
     *
     * @return true if "ok" game back as Answer
     */
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

    /**
     * If the Message "load" is received a new DocumentWriter is created to read the file with
     * the File, which name is in an Array on [1] when the getMessage was split. When the Writer is
     * also set the Game will be loaded and the Answer will be sent to the Server.
     *
     * @return true if ok was send
     */
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

    public static Network getNetplay() {
        return netplay;
    }

    /**
     * Send to the other Player the Coordinates where the User wants to shoot and gets an answer if he hitted something
     * or a Miss
     *
     * @param x Coordinate
     * @param y Coordinate
     * @return a number that indicates if a Shot missed (= 0), hit a ship (= 1)
     * or destroyed a Ship (= 2) or when an Error occurred (= -1)
     */
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
                        outStream.write(String.format("%s%n", "pass"));
                        outStream.flush();
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

    /**
     * When the message is "shot", the message is split and the  value of [1] and [2] of the Array are
     * pared to Integer to resemble the Coordinates where the other Player has shot at.
     * If the message is "save", a DocumentWriter is created and the save-Method of the controller is
     * called. If an Error comes up the value of the first index is 99.
     *
     * @return an int Array with the first number indicates a Shot (= 0), save (= 1) or an
     * Error (= 99)
     */
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

    /**
     * Send an Answer to the other Player if his Shot has hit something or not.
     * @param nr Number that indicates if a Shot was a Miss, Hit or Hit and Sunk
     */
    public void sendAnswer(int nr) {
        String message = "answer ";
        System.out.println("answer " + nr);
        try {
            outStream.write(String.format("%s%n", message + nr));
            outStream.flush();
            if (nr == 2) {
                message = inStream.readLine();
                if (!(message.startsWith("pass"))) {
                    sendAnswer(nr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}