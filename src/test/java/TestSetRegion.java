import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class TestSetRegion {

    private static String SCRIPT = """
            window.navigator.geolocation.getCurrentPosition = function(success) {
            var position = {
                "coords": {
                  "latitude": "555",
                  "longitude": "999"
                }
              };
              success(position);
            }
                        
            """;

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1); // Разрешить доступ к геолокации
        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = new ChromeDriver(options);

        // Создание объекта местоположения


// Установка местоположения в WebDriver
        ((JavascriptExecutor) driver).executeScript("navigator.geolocation.getCurrentPosition = function(success) {" +
                "    var position = { coords : { latitude: " + 59.937500 + ", " +
                "                              longitude: " + 30.308611 + " } };" +
                "    success(position);" +
                "};");


        driver.get("https://megamarket.ru/");
    }


}
