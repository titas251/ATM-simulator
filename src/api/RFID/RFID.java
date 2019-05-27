package api.RFID;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RFID {
    public static String readID() {
        String ID = null;
        try {
            List <String> list = new ArrayList<String>();
            list.add("python3");
            list.add("read.py");

            ProcessBuilder build = new ProcessBuilder(list);
            build.directory(new File("/home/pi/Desktop/ATM/lib/python-scripts/"));

            Process process = build.start();
            process.waitFor();

            Scanner scanner = new Scanner(process.getInputStream());
            ID = scanner.next();
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ID;
    }
}
