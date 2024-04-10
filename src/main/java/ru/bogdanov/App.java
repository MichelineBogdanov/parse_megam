package ru.bogdanov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.config.Config;
import ru.bogdanov.config.SettingsDlg;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.export.ExcelExporter;
import ru.bogdanov.view.gui.DataTable;
import ru.bogdanov.view.gui.IntegerLabel;
import ru.bogdanov.workers.TaskParse;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel parent;
    private JTextField urlTF;
    private JLabel urlLbl;
    private JToolBar toolBar;
    private JPanel mainPanel;
    private DataTable resultTable;
    private JScrollPane tableScroll;
    private JButton startBtn;
    private JButton exportBtn;
    private JButton stopBtn;
    private JButton scheduleBtn;
    private JLabel rowCount;
    private IntegerLabel count;
    private JButton clearBtn;
    private JProgressBar progress;
    private JButton settingsBtn;
    private JLabel progressLbl;
    private JPanel footer;

    private Config config = new Config();
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private Thread curThread;

    public static void main(String[] args) {
        App app = new App();
    }

    public App() {
        setContentPane(parent);
        setTitle(Constants.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        exportBtn.addActionListener(e -> onExport());
        scheduleBtn.addActionListener(e -> onSchedule());
        clearBtn.addActionListener(e -> onClear());
        settingsBtn.addActionListener(e -> onSettings());
        startBtn.addActionListener(e -> onStart());
        stopBtn.addActionListener(e -> onStop());
        setVisible(true);
        LOG.info("Приложение запустилось!");
    }

    private void onExport() {
        ExcelExporter excelExporter = new ExcelExporter(this);
        excelExporter.saveFile(resultTable);
    }

    private void onSchedule() {

    }

    private void onClear() {
        resultTable.removeData();
        resultTable.repaint();
        count.setValue(0);
    }

    private void onSettings() {
        SettingsDlg settingsDlg = new SettingsDlg(config);
        config = settingsDlg.getConfig();
    }

    private void onStart() {
        curThread = new Thread(new TaskParse(config, urlTF.getText(), resultTable, count));
        curThread.start();
    }

    private void onStop() {
        if (curThread != null) {
            curThread.interrupt();
            LOG.info("Парсинг остановлен!");
        }
    }

    private void createUIComponents() {
        resultTable = new DataTable();
    }
}
