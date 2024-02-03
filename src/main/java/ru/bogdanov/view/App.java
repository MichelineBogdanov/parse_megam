package ru.bogdanov.view;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private JPanel parent;
    private JTextField urlTF;
    private JLabel urlLbl;
    private JToolBar toolBar;
    private JPanel mainPanel;
    private JTable table;
    private JScrollPane tableScroll;
    private JButton startBtn;
    private JButton exportBtn;

    public static void main(String[] args) {
        App app = new App();
    }

    public App() {
        setContentPane(parent);
        setTitle("Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        setVisible(true);
    }
}
