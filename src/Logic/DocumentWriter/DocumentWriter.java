package Logic.DocumentWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class DocumentWriter {
    private static FileWriter myWriter;
    private static File myObj;
    private static ArrayList<String> text = new ArrayList<>();
    private static String id;

    public DocumentWriter(Timestamp t) {
       LocalDateTime time =  t.toLocalDateTime();

       id = time.getDayOfMonth() + "_" + time.getMonthValue() + "_" + time.getYear() + "_" + time.getHour() + "_" + time.getMinute() +"_"+ time.getSecond();

        String fs = System.getProperty("file.separator");
        String path = "src" + fs + "Data" + fs + id + ".txt" + fs;

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

    public static void writeShot(int x, int y) {
        text.add("shot " + x + " " + y + "\n");
    }

    public static void writeDone() {
        text.add("done\n");
    }

    public static void writeAnswer(int a) {
        text.add("answer " + a + "\n");
    }

    public static void writeReady() {
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


    public static void save() {
        for (int i = 0; i < text.size(); i++) {
            try {
                myWriter.write(text.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
