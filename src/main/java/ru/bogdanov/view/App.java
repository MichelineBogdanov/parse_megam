package ru.bogdanov.view;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.Request;
import org.openqa.selenium.devtools.v120.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.entity.megam_beans.Item;
import ru.bogdanov.entity.megam_beans.Root;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JLabel saleLbl;
    private JTextField saleTF;
    private JButton scheduleBtn;
    private JLabel rowCount;
    private JLabel count;
    private JButton clearBtn;

    public static void main(String[] args) {
        App app = new App();
    }

    public App() {
        setContentPane(parent);
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        startBtn.addActionListener(e -> onStart());
        stopBtn.addActionListener(e -> onStop());
        clearBtn.addActionListener(e -> onClear());
        setVisible(true);
    }

    private void onClear() {
        resultTable.removeData();
        resultTable.repaint();
        count.setText("0");
    }

    private void onStop() {
        System.out.println("Stop!");
    }

    private void onStart() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        String url = urlTF.getText();
        ChromeDriver driver = new ChromeDriver(options);
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
            Request request = requestWillBeSent.getRequest();
            //System.out.println(request.getUrl());
        });
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            if (response.getUrl().equals("https://megamarket.ru/api/mobile/v1/catalogService/catalog/search")) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                System.out.println(body);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                putData(body);
            }

        });
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("header-region-selector-view__footer-ok"))).click();
        String pageSource = driver.getPageSource();
        String pagesCount = driver.findElement(By.className("catalog-header__count")).getText().replaceAll("\\D", "");
        int pages = Integer.parseInt(pagesCount);
        System.out.println(pages);
        for (int i = 0; i < 2; i++) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.next > a")));
                Thread.sleep(3000);
                ((JavascriptExecutor) driver).executeScript("document.querySelector(\"li.next > a\").click();");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void putData(String json) {
        Gson gson = new Gson();
        Root root = gson.fromJson(json, Root.class);
        ArrayList<Item> items = root.getItems();
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        for (Item item : items) {
            if (item.getBonusPercent() > Integer.parseInt(saleTF.getText() == null || saleTF.getText().isEmpty() ? "0" : saleTF.getText())) {
                model.addRow(new Object[]{item.getGoods().getTitle(), item.getPrice(), item.getBonusPercent(), item.getGoods().getWebUrl()});
                resultTable.repaint();
                int i = Integer.parseInt(count.getText());
                count.setText(String.valueOf(++i));
            }
        }
    }

    private void rollPage() {

    }

    private void createUIComponents() {
        resultTable = new DataTable();
        // TODO: place custom component creation code here
    }
}
