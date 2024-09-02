package ru.bogdanov.workers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.devtools.v124.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bogdanov.app.UICallback;
import ru.bogdanov.config.Config;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.entity.BrowserFactory;

import java.time.Duration;
import java.util.Optional;

public class ParseLoader implements Runnable, Loader {

    private static final Logger LOG = LoggerFactory.getLogger(ParseLoader.class);

    private final BrowserFactory browserFactory = BrowserFactory.getFactory();
    private final Config config;
    private final String url;
    private final UICallback ui;
    private boolean canceled = false;
    private boolean executed = false;

    public ParseLoader(Config config, String url, UICallback ui) {
        this.config = config;
        this.url = url;
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            ChromeDriver browser = browserFactory.getBrowser();
            DevTools devTools = browser.getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.addListener(Network.responseReceived(), responseReceived -> {
                Response response = responseReceived.getResponse();
                if (response.getUrl().equals(Constants.FETCH_REQUEST)) {
                    String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                    ui.putTableData(body);
                }
            });
            browser.get(url);
            Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(20));
            setRegion(wait);
            String pagesCount = browser.findElement(By.className("catalog-header__count")).getText().replaceAll("\\D", "");
            int pages = Integer.parseInt(pagesCount) / 44;
            for (int i = 0; i < pages && !canceled; i++) {
                ui.setProgress((100 * i / pages));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.next > a")));
                Thread.sleep(3000);
                ((JavascriptExecutor) browser).executeScript("document.querySelector(\"li.next > a\").click();");
            }
            devTools.disconnectSession();
        } catch (InterruptedException e) {
            LOG.error("Ожидание не завершилось");
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

    @Override
    public synchronized void executeTask() {
        if (executed) {
            throw new IllegalStateException("Loader is already executed");
        }
        executed = true;
        canceled = false;
        Thread t = new Thread(this, "Custom loader thread");
        t.start();
    }

    @Override
    public synchronized void stop() {
        canceled = true;
        executed = false;
    }

    @Override
    public void pause() {

    }

    public String getUrl() {
        return url;
    }
}
