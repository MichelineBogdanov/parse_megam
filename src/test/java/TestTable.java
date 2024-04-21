import javax.swing.*;
import java.awt.*;

public class TestTable extends JFrame {
    private JTable table1;
    private JPanel panel1;

    public static void main(String[] args) {
        new TestTable();
    }

    public TestTable() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));

        setVisible(true);
    }
}
