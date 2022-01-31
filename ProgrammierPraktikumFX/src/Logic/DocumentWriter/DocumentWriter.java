package Logic.DocumentWriter;

import Logic.Game.GameElement;
import Logic.main.LogicConstants;
import Logic.main.Ship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class DocumentWriter {

    //region Variables
    private FileWriter myWriter;
    private Scanner myScanner;
    private File myObj;
    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<String> output;
    private String id;
    private String path;
    //endregion

    public static boolean deleteFile(String s) {
        File myObj = new File("src/SaveFiles/" + s);
        if (myObj.exists()) {
            return myObj.delete();
        } else {
            return false;
        }
    }

    public static boolean deleteOnlineFile(String s) {
        File myObj;
        boolean answer = false;
        myObj = new File("src/SaveFilesOnline/" + s);
        if (myObj.exists()) {
            answer = myObj.delete();
            myObj = new File("src/SaveFilesClient/" + s);
            myObj.delete();
        }

        return answer;
    }

    /**
     * Save the Game in its current State, with a different individual Name for each File. If an
     * Online Game is saved, the Directory is a different, than to a normal Game.
     * @param t Id that is given a Files a Name
     * @param online Indicates if the that is saved is an Online or Offline Game
     */
    public DocumentWriter(Timestamp t, boolean online) {
        LocalDateTime time = t.toLocalDateTime();

        id = time.getDayOfMonth() + "_" + time.getMonthValue() + "_" + time.getYear() + "-" + time.getHour() + "_" + time.getMinute() + "_" + time.getSecond();
        String fs = System.getProperty("file.separator");
        if (online) {
            path = "src" + fs + "SaveFilesOnline" + fs + id + ".txt" + fs;
        } else {
            path = "src" + fs + "SaveFiles" + fs + id + ".txt" + fs;
        }
    }

    /**
     * Save the Game in its current State, with a Name given from the User the File. If an
     * Online Game is saved, the Directory is a different, than to a normal Game.
     * @param s Name of the File
     * @param online Indicates if the that is saved is an Online or Offline Game
     */
    public DocumentWriter(String s, boolean online) {

        id = s;
        String fs = System.getProperty("file.separator");
        if (online) {
            path = "src" + fs + "SaveFilesOnline" + fs + id + ".txt" + fs;
        } else {
            path = "src" + fs + "SaveFiles" + fs + id + ".txt" + fs;
        }
    }

    /**
     * Save the Game in its current State, with a Name given from the User the File. If an
     * Online Game is saved, the Directory is a different, than to a normal Game.If the Enemy
     * send the Message the File is also saved in another Directory.
     * @param s Name of the File
     * @param online Indicates if the that is saved is an Online or Offline Game
     * @param client Indicates if in a Network Game the Client gets the Message to save
     */
    public DocumentWriter(String s, boolean online, boolean client) {
        this(s, online);
        String fs = System.getProperty("file.separator");
        if (client) {
            path = "src" + fs + "SaveFilesClient" + fs + id + ".txt" + fs;
        }
    }

    public String getId() {
        return id;
    }

    public void writeShot(int x, int y) {
        text.add("shot " + x + " " + y + "\n");
    }

    public void writeSize(int size) {
        text.add("size " + size + "\n");
    }

    public void writeGameMode(LogicConstants.GameMode m) {
        text.add("gameMode " + m + "\n");
    }

    public void writeShips(int two, int three, int four, int five) {
        int i = 0;
        String ships = "ships";

        ships += " " + two;
        ships += " " + three;
        ships += " " + four;
        ships += " " + five;

        text.add(ships + "\n");
    }

    public void writeShipsDestroyed(int two, int three, int four, int five) {
        int i = 0;
        String ships = "shipsDestroyed";

        ships += " " + two;
        ships += " " + three;
        ships += " " + four;
        ships += " " + five;

        text.add(ships + "\n");
    }


    public void writeMyGameField(GameElement[][] gameField) {
        writeGameField("MyGame", gameField);
    }

    public void writeEnemyGameField(GameElement[][] gameField) {
        writeGameField("EnemyGame", gameField);
    }

    /**
     * This Method writes each status of the Panes in the PlayingField and the Users informing GridPane.
     * First Part of the String is on which GridPane the Pane is located. After that the x- and y-Coordinate
     * of the Pane in the GridPane is written. As last Part the Status of this Pane is through a Numbercode
     * is saved in the File. This is done for evey Pane in both GridPanes
     * @param header In the File to identify if the Ship is on the Playing GridPane or on the informing on the left
     * @param gameField The Array where the Ships or Hits and Misses are saved
     */
    private void writeGameField(String header, GameElement[][] gameField) {
        String gameElementInfo;
        Ship shipInfo;
        for (int x = 0; x < gameField.length; x++) {
            for (int y = 0; y < gameField[0].length; y++) {

                gameElementInfo = header + " " + x + " " + y;

                switch (gameField[x][y].getStatus()) {

                    case WATER:
                        gameElementInfo += " 0\n";
                        break;
                    case SHIP:
                        gameElementInfo += " 1";
                        shipInfo = gameField[x][y].getShip();
                        if (shipInfo != null) {
                            gameElementInfo += shipInfo.toString() + "\n";
                        }
                        break;
                    case MISS:
                        gameElementInfo += " 2\n";
                        break;
                    case CLOSE:
                        gameElementInfo += " 3\n";
                        break;
                    case ERROR:
                        gameElementInfo += " 4\n";
                        break;
                    case HIT:
                        gameElementInfo += " 5";
                        shipInfo = gameField[x][y].getShip();
                        if (shipInfo != null) {
                            gameElementInfo += shipInfo.toString() + "\n";
                        } else {
                            gameElementInfo += "\n";
                        }
                        break;
                }
                text.add(gameElementInfo);
            }
        }
    }

    public void writeInitiator(boolean isInitiator) {
        text.add("init " + isInitiator + "\n");
    }

    /**
     * Creates an ArrayList, in which the lines of the File are put in.
     * @return an Arraylist with each line of the File in a Field
     */
    public ArrayList<String> load() {

        if (output == null || output.isEmpty()) {
            output = new ArrayList<>();

            if (myScanner == null) {
                try {
                    myObj = new File(path);
                    myScanner = new Scanner(myObj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            while (myScanner.hasNextLine()) {
                output.add(myScanner.nextLine());
            }
        }
        return output;
    }

    /**
     * Writes in the File myObj the content of the text-ArrayList.
     */
    public void save() {
        try {
            if (myObj == null && myWriter == null) {
                myObj = new File(path);
            }
            if (myWriter == null) {
                myWriter = new FileWriter(myObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < text.size(); i++) {
            try {
                myWriter.write(text.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the open File
     */
    public void close() {
        try {
            if (myWriter != null) {
                myWriter.close();
            }
            if (myScanner != null) {
                myScanner.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates an ArrayList with every File in src/SaveFiles
     * @return an Arraylist with every File in the Folder SaveFiles
     */
    public static ArrayList<String> getAllSaveFiles() {
        File folder = new File("src/SaveFiles/");
        ArrayList<String> files = new ArrayList<>();

        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    files.add(file.getName());
                }
            }
        }

        return files;
    }

    /**
     * Creates an ArrayList with every File in src/SaveFilesOnline
     * @return an Arraylist with every File in the Folder SaveFilesOnline
     */
    public static ArrayList<String> getAllOnlineSaveFiles() {
        File folder = new File("src/SaveFilesOnline/");
        ArrayList<String> files = new ArrayList<>();

        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }
}
