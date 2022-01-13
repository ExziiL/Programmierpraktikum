package Logic.DocumentWriter;

public class DocTester {

    public static void main(String[] args) {

        DocumentWriter writer = new DocumentWriter("test");

        writer.writeShot(1, 2);
        writer.close();

    }
}
