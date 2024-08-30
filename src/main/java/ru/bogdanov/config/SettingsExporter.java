package ru.bogdanov.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class SettingsExporter {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsExporter.class);

    private static final String path;

    static {
        try {
            path = new File(SettingsExporter.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParent() + "/cfg.json";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(path);
    }

    public SettingsExporter() {
    }

    public void saveConfig(Config config) {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, Config.class, writer);
            writer.flush();
        } catch (IOException e) {
            LOG.error("Ошибка записи json конфигурации файла");
        }
    }

    public Config loadConfig() {
        if (new File(path).exists()) {
            try (FileReader reader = new FileReader(path)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                return gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                LOG.error("Ошибка записи json конфигурации файла");
            }
        }
        return new Config();
    }

}
