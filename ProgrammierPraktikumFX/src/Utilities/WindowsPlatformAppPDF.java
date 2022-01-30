package Utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WindowsPlatformAppPDF {
    /**
     * shows the PDF Manual
     */
    public static void showManual() {
        try {
            File manual = (new File("src/assets/Manual/Klausur2.pdf"));
            if (manual.exists()) {
                Desktop.getDesktop().open(manual);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
