package com.franciscoabsl.catalog.adapter.out.logger;

import com.franciscoabsl.catalog.port.out.LoggerPort;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements LoggerPort {

    private final String logFile;

    public FileLogger(String logFile) {
        this.logFile = logFile;
    }

    @Override
    public void log(String message) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}