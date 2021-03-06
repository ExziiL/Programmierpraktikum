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
    private FileWriter myWriter;
    private Scanner myScanner;
    private File myObj;
    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<String> output;
    private String id;
    private String path;

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

    public DocumentWriter(String s, boolean online) {

        id = s;
        String fs = System.getProperty("file.separator");
        if (online) {
            path = "src" + fs + "SaveFilesOnline" + fs + id + ".txt" + fs;
        } else {
            path = "src" + fs + "SaveFiles" + fs + id + ".txt" + fs;
        }
    }

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

    // public void writeInitiator(boolean isInitiator){

    // }

    public void writeMyGameField(GameElement[][] gameField) {
        writeGameField("MyGame", gameField);
    }

    public void writeEnemyGameField(GameElement[][] gameField) {
        writeGameField("EnemyGame", gameField);
    }

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
