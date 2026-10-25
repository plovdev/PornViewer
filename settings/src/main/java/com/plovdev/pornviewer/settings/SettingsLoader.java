package com.plovdev.pornviewer.settings;

import com.plovdev.pornviewer.core.models.app.AppSettings;
import com.plovdev.pornviewer.security.CryptoEngine;
import com.plovdev.pornviewer.security.CryptoUtils;
import com.plovdev.pornviewer.security.PVSecurityManager;
import com.plovdev.pornviewer.security.RegisteredSecurityModule;
import com.plovdev.pornviewer.services.exceptions.SettingsLoadingException;
import com.plovdev.pornviewer.services.files.PVFileManager;
import com.plovdev.pornviewer.services.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class SettingsLoader {
    private static final Logger log = LoggerFactory.getLogger(SettingsLoader.class);
    private static final byte[] SETTINGS_AEAD_DATA = "SETT".getBytes(StandardCharsets.US_ASCII);

    public static AppSettings loadSettings() {
        if (!Files.exists(PVFileManager.getPvAppSettingsPath())) {
            throw new SettingsLoadingException("Settings file not found");
        }

        try {
            byte[] settingsFileData = Files.readAllBytes(PVFileManager.getPvAppSettingsPath());
            byte[] nonce = new byte[12];
            byte[] salt = new byte[16];
            if (settingsFileData.length < 28 + 16) { // 28 header + 16 tag(min)
                throw new SettingsLoadingException("Settings file too short: " + settingsFileData.length);
            }
            int settingsDataLength = settingsFileData.length - 28; // nonce + salt

            System.arraycopy(settingsFileData, 0, nonce, 0, 12);
            System.arraycopy(settingsFileData, 12, salt, 0, 16);

            CryptoEngine engine = new CryptoEngine(Cipher.DECRYPT_MODE, PVSecurityManager.getPassword(RegisteredSecurityModule.SETTINGS), salt);
            byte[] jsonData = engine.processData(settingsFileData, 28, settingsDataLength, nonce, SETTINGS_AEAD_DATA);
            try {
                return JSONSerializer.deserialize(jsonData, AppSettings.class);
            } finally {
                Arrays.fill(jsonData, (byte) 0);
            }
        } catch (Exception e) {
            log.error("Error load settings: ", e);
            throw new SettingsLoadingException(e);
        }
    }

    public static void saveSettings(AppSettings settings) {
        String settingsJson = JSONSerializer.serialize(settings);
        byte[] settingsFileData = settingsJson.getBytes(StandardCharsets.UTF_8);
        byte[] nonce = new byte[12];
        byte[] salt = new byte[16];
        byte[] nonceAndSalt = new byte[28];
        CryptoUtils.fillRandomBytes(nonce);
        CryptoUtils.fillRandomBytes(salt);
        System.arraycopy(nonce, 0, nonceAndSalt, 0, 12);
        System.arraycopy(salt, 0, nonceAndSalt, 12, 16);

        try {
            CryptoEngine engine = new CryptoEngine(Cipher.ENCRYPT_MODE, PVSecurityManager.getPassword(RegisteredSecurityModule.SETTINGS), salt);
            byte[] encryptedSettings = engine.processData(settingsFileData, nonce, SETTINGS_AEAD_DATA);
            Files.write(PVFileManager.getPvAppSettingsPath(), nonceAndSalt, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            Files.write(PVFileManager.getPvAppSettingsPath(), encryptedSettings, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            log.error("Error save settings: ", e);
            throw new SettingsLoadingException("Error save settings", e);
        } finally {
            Arrays.fill(settingsFileData, (byte) 0);
            Arrays.fill(nonceAndSalt, (byte) 0);
            Arrays.fill(nonce, (byte) 0);
            Arrays.fill(salt, (byte) 0);
        }
    }
}