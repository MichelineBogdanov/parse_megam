package ru.bogdanov.view.forms;

import ru.bogdanov.view.gui.DataTable;
import ru.bogdanov.view.gui.IntegerLabel;

import javax.swing.*;

public class TaskWindow {
    private JPanel parent;
    private JPanel mainPanel;
    private JLabel urlLbl;
    private JTextField urlTF;
    private JButton startBtn;
    private JButton stopBtn;
    private DataTable resultTable;
    private JLabel rowCount;
    private JLabel progressLbl;
    private JProgressBar progress;
    private IntegerLabel count;
    private JButton configBtn;
    private JLabel configLbl;
    private JTextField configTF;
    private JButton pauseBtn;
    private JPanel footer;
}
