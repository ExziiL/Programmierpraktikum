package Logic.DocumentWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentWriter {
    FileWriter myWriter;
    File myObj;
    ArrayList<String> text = new ArrayList<>();

    public DocumentWriter(String name) {
        //  String path = "C:\\Users\\Daniel\\OneDrive - bwedu\\Desktop\\Dateien" + name + ".txt";
        String path = name + ".txt";

        try {
            myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File Created");
            }
            myWriter = new FileWriter(path);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void writeShot(int x, int y) {
        text.add("(" + x + "," + y + ")");
    }

    public void writeSize(int size) {
        text.add("size " + size);
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

        text.add(ships);
    }

    public void close() {
        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
