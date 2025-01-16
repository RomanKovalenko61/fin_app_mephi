package ru.kovalenko.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File(getPropsDir(), "config\\app.properties");

    private static final Config INSTANCE = new Config();

    private Config() {
        try {
            InputStream is = Files.newInputStream(PROPS.toPath());
            Properties props = new Properties();
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Не найден файл конфигурации " + PROPS.getAbsolutePath());
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    private static File getPropsDir() {
        String prop = System.getProperty("finAppPropertyDir");
        File propDir = new File(prop == null ? "." : prop);
        if (!propDir.isDirectory()) {
            throw new IllegalStateException(propDir + " Не является каталогом");
        }
        return propDir;
    }
}
