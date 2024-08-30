package ru.bogdanov.app;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.config.Config;
import ru.bogdanov.config.SettingsDlg;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.entity.megam_beans.Item;
import ru.bogdanov.entity.megam_beans.Root;
import ru.bogdanov.export.ExcelExporter;
import ru.bogdanov.view.gui.common.IntegerLabel;
import ru.bogdanov.view.gui.item.ItemTable;
import ru.bogdanov.view.gui.task.TaskTable;
import ru.bogdanov.workers.swing_worker.SWorkerParseLoader;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class App extends JFrame implements UICallback {

    private JPanel parent;
    private JTextField urlTF;
    private JLabel urlLbl;
    private JToolBar toolBar;
    private JPanel mainPanel;
    private ItemTable itemTable;
    private JButton startBtn;
    private JButton exportBtn;
    private JButton stopBtn;
    private JButton scheduleBtn;
    private JLabel rowCountLbl;
    private IntegerLabel countLbl;
    private JButton clearBtn;
    private JProgressBar progress;
    private JButton settingsBtn;
    private JLabel progressLbl;
    private JPanel footerPanel;
    private JPanel addPanel;
    private JButton addTaskBtn;
    private JPanel taskTrackerPanel;
    private JButton starBtn;
    private JButton pauseBtn;
    private TaskTable taskTable;
    private JScrollPane itemScrollPane;
    private JScrollPane taskScrollPane;
    private JSplitPane tableSplitPane;
    private JButton refreshBtn;
    private JScrollPane testSP;

    private Config config = new Config();
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        App app = new App();
    }

    public App() {
        this.add(parent);
        setTitle(Constants.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        exportBtn.addActionListener(e -> onExport());
        clearBtn.addActionListener(e -> onClear());
        settingsBtn.addActionListener(e -> onSettings());
        startBtn.addActionListener(e -> startParsing());
        pauseBtn.addActionListener(e -> onPause());
        stopBtn.addActionListener(e -> stopParsing());
        refreshBtn.addActionListener(e -> onRefresh());
        addTaskBtn.addActionListener(e -> createTask());
        this.setVisible(true);
        LOG.info("Приложение запустилось!");
    }

    private void onRefresh() {
        taskTable.refreshTasks();
    }

    private void onPause() {
        taskTable.pauseTask();
    }

    private void createTask() {
        String urlTFText = urlTF.getText();
        try {
            new URL(urlTFText);
            SWorkerParseLoader parseLoader = new SWorkerParseLoader(this, config, urlTFText);
            taskTable.addTask(parseLoader);
            urlTF.setText(null);
            LOG.info("Создана задача. Настройки : " + config);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "Введен невалидный URL\n" + urlTFText);
            LOG.error("Введен невалидный URL");
        }
    }

    private void onExport() {
        ExcelExporter excelExporter = new ExcelExporter(this);
        excelExporter.saveFile(itemTable);
    }

    private void onClear() {
        itemTable.removeData();
        countLbl.setValue(0);
        progress.setValue(0);
    }

    private void onSettings() {
        SettingsDlg settingsDlg = new SettingsDlg();
        config = settingsDlg.getConfig();
    }

    @Override
    public void putTableData(String json) {
        Gson gson = new Gson();
        Root root = gson.fromJson(json, Root.class);
        ArrayList<Item> items = root.getItems();
        for (Item item : items) {
            if (item.getBonusPercent() > config.getSale() && item.getRating() > config.getRate()) {
                Object[] data = {item.getGoods().getTitle(),
                        item.getPrice(),
                        item.getBonusPercent(),
                        (item.getPrice() * (100 - item.getBonusPercent())) / 100,
                        item.getRating(),
                        item.getGoods().getWebUrl()};
                itemTable.addRow(data);
                increaseCounter();
            }
        }
    }

    @Override
    public void increaseCounter() {
        countLbl.increaseValue();
    }

    @Override
    public void setProgress(int progressPercent) {
        progress.setValue(progressPercent);
    }

    @Override
    public void startParsing() {
        taskTable.startAll();
    }

    @Override
    public void stopParsing() {
        taskTable.stopAll();
    }

    @Override
    public void showError(String message) {

    }

    private void createUIComponents() {
        itemTable = new ItemTable();
        taskTable = new TaskTable();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
