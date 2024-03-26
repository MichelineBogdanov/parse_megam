package ru.bogdanov;

import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.Request;
import org.openqa.selenium.devtools.v120.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.bogdanov.config.Config;
import ru.bogdanov.config.SettingsDlg;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.entity.megam_beans.Item;
import ru.bogdanov.entity.megam_beans.Root;
import ru.bogdanov.view.gui.DataTable;
import ru.bogdanov.view.gui.IntegerLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;

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
    private JTextField saleTF;
    private JButton scheduleBtn;
    private JLabel rowCount;
    private IntegerLabel count;
    private JButton clearBtn;
    private JProgressBar progress;
    private JButton settingsBtn;
    private JLabel progressLbl;

    private Config config;

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
    }

    private void onExport() {

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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        ChromeDriver driver = new ChromeDriver(options);
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
            Request request = requestWillBeSent.getRequest();
        });
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            if (response.getUrl().equals(Constants.FETCH_REQUEST)) {
                String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                putData(body);
            }
        });
        String url = urlTF.getText();
        driver.get(url);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        setRegion(wait);
        String pagesCount = driver.findElement(By.className("catalog-header__count")).getText().replaceAll("\\D", "");
        int pages = Integer.parseInt(pagesCount);
        for (int i = 0; i < pages / 44; i++) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.next > a")));
                Thread.sleep(3000);
                ((JavascriptExecutor) driver).executeScript("document.querySelector(\"li.next > a\").click();");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void onStop() {
        System.out.println("Stop!");
    }

    private void setRegion(Wait<WebDriver> wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-bordered.header-region-selector-view__footer-cancel"))).click();
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Регион или город']")));
        element.click();
        element.sendKeys("Санкт-Петербург");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("form > button"))).click();
    }

    private void putData(String json) {
        Gson gson = new Gson();
        Root root = gson.fromJson(json, Root.class);
        ArrayList<Item> items = root.getItems();
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        for (Item item : items) {
            if (item.getBonusPercent() > Integer.parseInt(saleTF.getText() == null || saleTF.getText().isEmpty() ? "0" : saleTF.getText())) {
                model.addRow(new Object[]{item.getGoods().getTitle(),
                        item.getPrice(),
                        item.getBonusPercent(),
                        (item.getPrice() * (100 - item.getBonusPercent())) / 100,
                        item.getGoods().getWebUrl()});
                count.increaseValue();
                resultTable.repaint();
            }
        }
    }

    private void createUIComponents() {
        resultTable = new DataTable();
    }
}
