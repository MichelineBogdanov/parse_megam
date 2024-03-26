package ru.bogdanov.config;

import ru.bogdanov.constants.Constants;

import javax.swing.*;
import java.awt.*;

public class SettingsDlg extends JDialog {

    private JPanel parent;
    private JCheckBox fixAppCB;
    private JCheckBox openBrowserCB;
    private JSeparator separator1;
    private JSeparator separator2;
    private JTextField saleTF;
    private JTextField rateTF;
    private JLabel saleLbl;
    private JLabel rateLbl;
    private JButton saveBtn;
    private JPanel allSettings;
    private JPanel appSettings;
    private JPanel browserSettings;
    private JPanel itemSettings;

    private Config config;

    public SettingsDlg(Config config) {
        setModal(true);
        setContentPane(parent);
        setTitle(Constants.SETTINGS_TITLE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 400));
        saveBtn.addActionListener(e -> onSaveSettings());
        setConfigToIU(config);
        setVisible(true);
    }

    private void setConfigToIU(Config config) {
        if (config != null) {
            this.config = config;
            fixAppCB.setSelected(config.isFixApp());
            openBrowserCB.setSelected(config.isOpenBrowser());
            saleTF.setText(String.valueOf(config.getSale()));
            rateTF.setText(String.valueOf(config.getRate()));
        } else {
            this.config = new Config();
        }
    }

    private void onSaveSettings() {
        config.setFixApp(fixAppCB.isSelected());
        config.setOpenBrowser(openBrowserCB.isSelected());
        config.setSale(Integer.parseInt(saleTF.getText()));
        config.setRate(Double.parseDouble(rateTF.getText()));
        this.dispose();
    }

    public Config getConfig() {
        return config;
    }
}
