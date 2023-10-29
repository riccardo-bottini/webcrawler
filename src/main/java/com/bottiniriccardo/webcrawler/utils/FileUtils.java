package com.bottiniriccardo.webcrawler.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import com.bottiniriccardo.webcrawler.Main;

public class FileUtils {
    private static final Logger logger = Logger.getLogger(FileUtils.class.getName());

    /**
     * Write the crawled URLs in a txt file
     */
    public static void writeToAFile() {
        String filePath = "output.txt";

        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : Main.visitedUrls) {
                writer.write(line); // Write the string in the file
                writer.newLine(); // Add a new line
            }

            logger.info("Output is stored in " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
