package com.plovdev.pornviewer.services.files;

import com.plovdev.pornviewer.core.models.app.AppInfo;
import com.plovdev.pornviewer.services.exceptions.ConfigLoadingException;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigReader {
    private final Properties properties = new Properties();
    private static final AtomicReference<AppInfo> APP_INFO_CACHE = new AtomicReference<>();

    public ConfigReader() {
        this("/.properties");
    }

    public ConfigReader(String name) {
        try {
            properties.load(ConfigReader.class.getResourceAsStream(name));
        } catch (Exception e) {
            throw new ConfigLoadingException("Error to load config " + name, e);
        }
    }

    public String getEnv(String path) {
        return properties.getProperty(path);
    }

    public String getEnv(String path, String def) {
        return properties.getProperty(path, def);
    }

    public int getInt(String path) {
        return Integer.parseInt(properties.getProperty(path));
    }

    public int getInt(String path, int def) {
        String property = properties.getProperty(path);
        if (property == null) {
            return def;
        }
        return Integer.parseInt(property);
    }

    public boolean getBoolean(String path) {
        return Boolean.parseBoolean(properties.getProperty(path));
    }

    public boolean getBoolean(String path, boolean def) {
        String property = properties.getProperty(path);
        if (property == null) {
            return def;
        }
        return Boolean.parseBoolean(property);
    }

    public synchronized static @NonNull AppInfo loadAppInfo() {
        if (APP_INFO_CACHE.get() == null) {
            ConfigReader reader = new ConfigReader();

            String appName = reader.getEnv("APP_NAME");
            String version = reader.getEnv("VERSION");
            String pvId = reader.getEnv("PV_ID");
            String buildId = reader.getEnv("BUILD_ID");
            LocalDate buildDate = LocalDate.parse(reader.getEnv("BUILD_DATE"), AppInfo.BUILD_DATE_FORMATTER_FROMSTR);
            String vendor = reader.getEnv("VEDNOR");
            String copyright = reader.getEnv("COPYRIGHT");

            AppInfo info = new AppInfo(appName, version, pvId, buildId, buildDate, vendor, copyright);
            APP_INFO_CACHE.set(info);
            return info;
        } else {
            return APP_INFO_CACHE.get();
        }
    }
}