package audit;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Audit {
    public static void writeAudit(String action) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("audit.csv", true))) {
            LocalTime lt = LocalTime.now();
            pw.println(action + " " + lt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
