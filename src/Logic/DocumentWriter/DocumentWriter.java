package Logic.DocumentWriter;

import Logic.Game.EnemyGame;
import Logic.Game.GameElement;

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
    private String id;
    private String path;

    public DocumentWriter(Timestamp t) {
        LocalDateTime time = t.toLocalDateTime();

        id = time.getDayOfMonth() + "_" + time.getMonthValue() + "_" + time.getYear() + "_" + time.getHour() + "_" + time.getMinute() + "_" + time.getSecond();

        String fs = System.getProperty("file.separator");
        path = "src" + fs + "Data" + fs + id + ".txt" + fs;

    }

    public DocumentWriter(String s) {

        id = s;
        String fs = System.getProperty("file.separator");
        path = "src" + fs + "Data" + fs + id + ".txt" + fs;
    }

    public void writeShot(int x, int y) {
        text.add("shot " + x + " " + y + "\n");
    }

    public void writeDone() {
        text.add("done\n");
    }

    public void writeAnswer(int a) {
        text.add("answer " + a + "\n");
    }

    public void writeReady() {
        text.add("ready" + "\n");
    }

    public void writeSize(int size) {
        text.add("size " + size + "\n");
    }

    public void writeShips(int two, int three, int four, int five) {
        int i = 0;
        String ships = "ships";
        for (i = 0; i < five; i++) {
            ships += " 5";
        }

        for (i = 0; i < four; i++) {
            ships += " 4";
        }
        for (i = 0; i < three; i++) {
            ships += " 3";
        }
        for (i = 0; i < two; i++) {
            ships += " 2";
        }

        text.add(ships + "\n");
    }

    public void writeShipsDestroyed(int two, int three, int four, int five) {
        int i = 0;
        String ships = "shipsDestroyed";
        for (i = 0; i < five; i++) {
            ships += " 5";
        }

        for (i = 0; i < four; i++) {
            ships += " 4";
        }
        for (i = 0; i < three; i++) {
            ships += " 3";
        }
        for (i = 0; i < two; i++) {
            ships += " 2";
        }

        text.add(ships + "\n");
    }

    public void writeMyGameField(GameElement[][] gameField) {
        writeGameField("MyGame", gameField);
    }

    public void writeEnemyGameField(GameElement[][] gameField) {
        writeGameField("EnemyGame", gameField);
    }

    private void writeGameField(String header, GameElement[][] gameField) {
        String position;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; i < gameField[0].length; i++) {

                position = header + " " + i + " " + j;

                switch (gameField[i][j].getStatus()) {

                    case WATER:
                        position += " 0\n";
                        break;
                    case SHIP:
                        position += " 1\n";
                        break;
                    case MISS:
                        position += " 2\n";
                        break;
                    case CLOSE:
                        position += " 3\n";
                        break;
                    case ERROR:
                        position += " 4\n";
                        break;
                    case HIT:
                        position += " 5\n";
                        break;
                }
                text.add(position);
            }
        }
    }

    public ArrayList<String> load() {

        ArrayList<String> output = new ArrayList<>();

        if (myObj == null && myWriter == null) {
            createInstance();
        }

        while (myScanner.hasNextLine()) {
            output.add(myScanner.nextLine());

        }
        return output;
    }

    public void save() {
        if (myObj == null && myWriter == null) {
            createInstance();
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
            myWriter.close();
            myScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllSaveFiles() {
        File folder = new File("src/Data/");
        ArrayList<String> files = new ArrayList<>();

        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                files.add(file.getName());
            }
        }
        return files;
    }

    private void createInstance() {
        try {
            myObj = new File(path);
            myWriter = new FileWriter(myObj);
            myScanner = new Scanner(myObj);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
