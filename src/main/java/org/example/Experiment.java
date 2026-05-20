package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Experiment {
    public static long run(String folder, int P) throws Exception {
        File[] files = new File(folder).listFiles(f -> f.getName().endsWith(".txt"));
        Arrays.sort(files, Comparator.comparing(File::getName));

        int total = files.length;
        int chunkSize = total / P;

        String classpath = System.getProperty("java.class.path");

        long startTime = System.currentTimeMillis();

        List<Process> processes = new ArrayList<>();

        for (int p = 0; p < P; p++) {
            int from = p * chunkSize;
            int to = (p == P - 1) ? total : from + chunkSize;


            List<String> cmd = new ArrayList<>();
            cmd.add("java");
            cmd.add("-cp");
            cmd.add(classpath);
            cmd.add("Worker");

            for (int i = from; i < to; i++) {
                cmd.add(files[i].getAbsolutePath());
            }

            Process proc = new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start();
            processes.add(proc);
        }

        for (Process proc : processes) {
            proc.waitFor();
        }

        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
