package ru.bogdanov.entity;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserFactory {

    public BrowserFactory() {
    }

    public static BrowserFactory getFactory() {
        return new BrowserFactory();
    }

    public ChromeDriver getBrowser() {
        ChromeOptions options = getChromeOptions();
        return new ChromeDriver(options);
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        return options;
    }

}
