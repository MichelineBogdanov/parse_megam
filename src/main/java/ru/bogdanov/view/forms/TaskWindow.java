package ru.bogdanov.view.forms;

import ru.bogdanov.view.gui.item.ItemTable;
import ru.bogdanov.view.gui.common.IntegerLabel;
import ru.bogdanov.app.UICallback;

import javax.swing.*;

public class TaskWindow implements UICallback {
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
