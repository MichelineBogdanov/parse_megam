package ru.bogdanov;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.Request;
import org.openqa.selenium.devtools.v120.network.model.Response;

import java.time.Duration;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
            Request request = requestWillBeSent.getRequest();
            System.out.println(request.getUrl());
        });
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            if (response.getUrl().equals("https://megamarket.ru/api/mobile/v1/catalogService/catalog/search")) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                String body = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                System.out.println(body);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
            }
        });
        driver.get("https://megamarket.ru/catalog/processory/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));
        String pageSource = driver.getPageSource();
        System.out.println(pageSource);

    }
}