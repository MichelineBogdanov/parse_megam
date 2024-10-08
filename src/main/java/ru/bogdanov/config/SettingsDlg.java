package ru.bogdanov.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.constants.Constants;

import javax.swing.*;
import java.awt.*;

public class SettingsDlg extends JDialog {

    private JPanel parent;
    private JCheckBox fixAppCB;
    private JCheckBox openBrowserCB;
    private JTextField saleTF;
    private JTextField rateTF;
    private JLabel saleLbl;
    private JLabel rateLbl;
    private JButton saveBtn;
    private JPanel appSettings;
    private JPanel itemSettings;
    private JTabbedPane settingsTabPane;
    private JTextField pagesCountTF;
    private JLabel pagesCountLbl;
    private JPanel browserSettings;

    private Config config;
    private final SettingsExporter configDealer = new SettingsExporter();
    private static final Logger LOG = LoggerFactory.getLogger(SettingsDlg.class);

    public SettingsDlg() {
        setModal(true);
        setContentPane(parent);
        setTitle(Constants.SETTINGS_TITLE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 400));
        saveBtn.addActionListener(e -> onSaveSettings());
        config = configDealer.loadConfig();
        setConfigToIU(config);
        setVisible(true);
    }

    private void setConfigToIU(Config config) {
        this.config = config;
        fixAppCB.setSelected(config.isFixApp());
        openBrowserCB.setSelected(config.isOpenBrowser());
        pagesCountTF.setText(String.valueOf(config.getPages()));
        saleTF.setText(String.valueOf(config.getSale()));
        rateTF.setText(String.valueOf(config.getRate()));
    }

    private void onSaveSettings() {
        try {
            config.setFixApp(fixAppCB.isSelected());
            config.setOpenBrowser(openBrowserCB.isSelected());
            config.setPages(Integer.parseInt(pagesCountTF.getText()));
            config.setSale(Integer.parseInt(saleTF.getText()));
            config.setRate(Double.parseDouble(rateTF.getText()));
            configDealer.saveConfig(config);
            LOG.info("Задана конфигурация: " + config.toString());
            this.dispose();
        } catch (NumberFormatException e) {
            LOG.error("Неверный формат числа");
            JOptionPane.showMessageDialog(this, "Введено число неверного формата\n"
                    + e.getMessage());
        }
    }

    public Config getConfig() {
        return config;
    }
}
