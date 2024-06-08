import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class TestTable extends JFrame {

    private JTable table;
    private JPanel parentPanel;
    private JScrollPane tablePanel;
    private JButton startButton;
    private JButton deleteButton;

    public static final Vector<String> HEADER = new Vector<>() {{
        add("TEST_HEADER_1");
        add("TEST_HEADER_2");
    }};
    Vector<Vector<String>> data = new Vector<>();

    public static void main(String[] args) {
        new TestTable();
    }

    public TestTable() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));

        DefaultTableModel model = new DefaultTableModel(data, HEADER);
        table.setModel(model);

        startButton.addActionListener(e -> {
            new Thread(this::fillTable).start();
        });

        add(parentPanel);
        setVisible(true);
    }

    private void fillTable() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                data.add(new Vector<>(List.of("TEST", "TEST")));
                ((DefaultTableModel) table.getModel()).fireTableDataChanged();
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
