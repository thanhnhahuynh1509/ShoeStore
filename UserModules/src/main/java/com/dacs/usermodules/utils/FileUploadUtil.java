package com.dacs.usermodules.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {

    public static void saveFile(String dir, String fileName, MultipartFile fileUpload) {
        Path dirPath = Paths.get(dir);
        try {
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path fullFile = dirPath.resolve(fileName);
            Files.write(fullFile, fileUpload.getBytes());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);
        try {
            Files.list(dirPath).forEach(file -> {
                if(!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            });
        } catch (IOException exception) {
            System.out.println("Can not find directory: " + dir);
        }
    }
}
