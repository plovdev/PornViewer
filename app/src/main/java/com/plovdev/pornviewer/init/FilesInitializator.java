package com.plovdev.pornviewer.init;

import com.plovdev.pornviewer.exceptions.PornViewerInitializationException;
import com.plovdev.pornviewer.services.files.PVFileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesInitializator implements AppInitializator {
    private static final Logger log = LoggerFactory.getLogger(FilesInitializator.class);

    @Override
    public InitType getInitType() {
        return InitType.INIT_FILES;
    }

    @Override
    public void initialize() {
        try {
            preparePath(PVFileManager.getPVBasePath(), true);
            preparePath(PVFileManager.getPvPluginsPath(), true);
            preparePath(PVFileManager.getPvDownloadsPath(), true);
            preparePath(PVFileManager.getPvSystemPath(), true);
            preparePath(PVFileManager.getPvDbPath(), false);
            preparePath(PVFileManager.getPvAppSettingsPath(), false);
        } catch (Exception e) {
            log.error("Error init files map: ", e);
            throw new PornViewerInitializationException(e);
        }
    }

    private void preparePath(Path path, boolean isDir) throws IOException {
        if (Files.notExists(path)) {
            if (isDir) {
                Files.createDirectories(path);
            } else {
                Files.createFile(path);
            }
        }
    }
}