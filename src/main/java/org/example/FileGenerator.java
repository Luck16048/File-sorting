package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class FileGenerator {
    public static void generate(String folder, int filesCount, int numbersPerFile) throws IOException {
        File dir = new File(folder);
        dir.mkdirs();

        File[] existing = dir.listFiles();
        if (existing != null) {
            for (File f : existing) f.delete();
        }

        Random rand = new Random(42);

        for (int i = 1; i <= filesCount; i++) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(folder + "/file_" + i + ".txt"))) {

                for (int j = 0; j < numbersPerFile; j++) {
                    writer.println(rand.nextInt(100000));
                }
            }
        }
        System.out.println("Generated " + filesCount + " files -> " + folder);

    }
}
