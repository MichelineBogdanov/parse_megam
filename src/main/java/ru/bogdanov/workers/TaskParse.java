package ru.bogdanov.workers;

import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.config.Config;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.entity.BrowserFactory;
import ru.bogdanov.entity.megam_beans.Item;
import ru.bogdanov.entity.megam_beans.Root;
import ru.bogdanov.view.gui.DataTable;
import ru.bogdanov.view.gui.IntegerLabel;

import javax.swing.table.DefaultTableModel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;

public class TaskParse implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TaskParse.class);

    private final BrowserFactory browserFactory = BrowserFactory.getFactory();
    private final Config config;
    private final String url;
    private final DataTable resultTable;
    private final IntegerLabel count;

    public TaskParse(Config config, String url, DataTable resultTable, IntegerLabel count) {
        this.config = config;
        this.url = url;
        this.resultTable = resultTable;
        this.count = count;
    }

    @Override
    public void run() {
        ChromeDriver browser = browserFactory.getBrowser();
        DevTools devTools = browser.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            if (response.getUrl().equals(Constants.FETCH_REQUEST)) {
                String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                putData(body);
            }
        });
        browser.get(url);
        Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(20));
        setRegion(wait);
        String pagesCount = browser.findElement(By.className("catalog-header__count")).getText().replaceAll("\\D", "");
        int pages = Integer.parseInt(pagesCount);
        for (int i = 0; i < pages / 44 && !Thread.currentThread().isInterrupted(); i++) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.next > a")));
                Thread.sleep(3000);
                ((JavascriptExecutor) browser).executeScript("document.querySelector(\"li.next > a\").click();");
            } catch (InterruptedException e) {
                LOG.error("Ожидание не завершилось");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void putData(String json) {
        Gson gson = new Gson();
        Root root = gson.fromJson(json, Root.class);
        ArrayList<Item> items = root.getItems();
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        for (Item item : items) {
            if (item.getBonusPercent() > config.getSale() && item.getRating() > config.getRate()) {
                model.addRow(new Object[]{item.getGoods().getTitle(),
                        item.getPrice(),
                        item.getBonusPercent(),
                        (item.getPrice() * (100 - item.getBonusPercent())) / 100,
                        item.getRating(),
                        item.getGoods().getWebUrl()});
                count.increaseValue();
                resultTable.repaint();
            }
        }
    }

    private void setRegion(Wait<WebDriver> wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-bordered.header-region-selector-view__footer-cancel"))).click();
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Регион или город']")));
        element.click();
        element.sendKeys(Constants.CITY);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOG.error("Ожидание не завершилось");
            throw new RuntimeException(e);
        }
        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("form > button"))).click();
    }

}
