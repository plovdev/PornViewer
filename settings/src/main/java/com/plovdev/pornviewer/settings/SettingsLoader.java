package com.plovdev.pornviewer.settings;

import com.plovdev.pornviewer.core.models.app.AppSettings;
import com.plovdev.pornviewer.security.CryptoEngine;
import com.plovdev.pornviewer.security.PVSecurityManager;
import com.plovdev.pornviewer.security.RegisteredSecurityModule;

import javax.crypto.Cipher;

public class SettingsLoader {
    public static AppSettings loadSettings() {
        CryptoEngine engine = new CryptoEngine(Cipher.DECRYPT_MODE, PVSecurityManager.getPassword(RegisteredSecurityModule.SETTINGS));

        return new AppSettings();
    }
}