package Logic.DocumentWriter;

import java.sql.Timestamp;
import java.time.Instant;

public class DocTester {

    public static void main(String[] args) {
        Timestamp instant= Timestamp.from(Instant.now());
        DocumentWriter writer = new DocumentWriter(instant,false);

        writer.writeShot(1, 2);
        writer.writeSize(4);
        writer.writeShips(1, 2, 3, 4);
        writer.save();
        writer.close();

    }
}
