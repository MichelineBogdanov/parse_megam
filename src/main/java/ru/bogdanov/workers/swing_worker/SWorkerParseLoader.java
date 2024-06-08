package ru.bogdanov.workers.swing_worker;

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
import ru.bogdanov.app.UICallback;
import ru.bogdanov.config.Config;
import ru.bogdanov.constants.Constants;
import ru.bogdanov.entity.BrowserFactory;
import ru.bogdanov.workers.Loader;

import javax.swing.*;
import java.time.Duration;
import java.util.*;

public class SWorkerParseLoader extends SwingWorker<Void, String> implements Loader {

    private static final Logger LOG = LoggerFactory.getLogger(SWorkerParseLoader.class);

    private final BrowserFactory browserFactory = BrowserFactory.getFactory();

    private final UICallback ui;
    private final Config config;
    private final String url;
    private final Date date = new Date();

    private boolean isPause;
    private final Object lock = new Object();

    public SWorkerParseLoader(UICallback ui, Config config, String url) {
        this.ui = ui;
        this.config = config;
        this.url = url;
    }

    @Override
    protected Void doInBackground() {
        ChromeDriver browser = browserFactory.getBrowser();
        DevTools devTools = browser.getDevTools();
        try {
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.addListener(Network.responseReceived(), responseReceived -> {
                Response response = responseReceived.getResponse();
                if (response.getUrl().equals(Constants.FETCH_REQUEST)) {
                    String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                    publish(body);
                }
            });
            browser.get(url);
            Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(20));
            setRegion(wait);
            String pagesCount = browser.findElement(By.className("catalog-header__count")).getText().replaceAll("\\D", "");
            int pages = config.getPages() > 0 ? config.getPages() : Integer.parseInt(pagesCount) / 44;
            for (int i = 0; i < pages && !isCancelled(); i++) {
                synchronized (lock) {
                    if (isPause) {
                        lock.wait();
                    }
                }
                setProgress(((100 * (i + 1)) / pages));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.next > a")));
                int delay = new Random().nextInt(4000) + 3000;
                Thread.sleep(delay);
                ((JavascriptExecutor) browser).executeScript("document.querySelector(\"li.next > a\").click();");
            }
        } catch (InterruptedException e) {
            LOG.error("Ожидание не завершилось");
        } finally {
            browser.quit();
        }
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        chunks.forEach(ui::putTableData);
        ui.setProgress(getProgress());
    }

    @Override
    public void executeTask() {
        this.execute();
    }

    @Override
    public void stop() {
        cancel(true);
    }

    @Override
    public void pause() {
        synchronized (lock) {
            if (isPause) {
                isPause = false;
                lock.notify();
            } else {
                isPause = true;
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

    public String getUrl() {
        return url;
    }

    public UICallback getUi() {
        return ui;
    }

    public Config getConfig() {
        return config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SWorkerParseLoader that = (SWorkerParseLoader) o;
        return Objects.equals(config, that.config) && Objects.equals(url, that.url) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(config, url, date);
    }
}
