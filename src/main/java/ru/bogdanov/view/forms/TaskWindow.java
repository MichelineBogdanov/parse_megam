package ru.bogdanov.view.forms;

import ru.bogdanov.app.UICallback;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.view.gui.common.IntegerLabel;
import ru.bogdanov.view.gui.item.ItemTable;

import javax.swing.*;
import java.awt.*;

public class TaskWindow extends JFrame implements UICallback {
    private JPanel parent;
    private JPanel mainPanel;
    private JLabel urlLbl;
    private JTextField urlTF;
    private JButton startBtn;
    private JButton stopBtn;
    private ItemTable itemTable;
    private JLabel rowCountLbl;
    private JLabel progressLbl;
    private JProgressBar progress;
    private IntegerLabel count;
    private JButton configBtn;
    private JLabel configLbl;
    private JTextField configTF;
    private JButton pauseBtn;
    private JPanel footerPanel;
    private JScrollPane tableScrollPanel;
    private JToolBar toolBar;
    private JButton exportBtn;
    private JButton clearBtn;

    public TaskWindow() {
        setContentPane(parent);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setTitle(Constants.TASK_WINDOW);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 500));
        setVisible(true);
    }

    @Override
    public void putTableData(String json) {

    }

    @Override
    public void increaseCounter() {

    }

    @Override
    public void setProgress(int progressPercent) {

    }

    @Override
    public void startParsing() {

    }

    @Override
    public void stopParsing() {

    }

    @Override
    public void showError(String message) {

    }
}
