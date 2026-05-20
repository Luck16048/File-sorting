package org.example;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Generating files...");
        FileGenerator.generate("data/small", 120, 2000);
        FileGenerator.generate("data/medium", 120, 5000);
        FileGenerator.generate("data/large", 120, 10000);

        int maxP = Runtime.getRuntime().availableProcessors();
        System.out.println("Core CPU: " + maxP + "\n");

        String[] folders = {"data/small", "data/medium", "data/large"};
        String[] names = {"Small", "Medium", "Large"};

        try (PrintWriter csv = new PrintWriter(new FileWriter("results.csv"))) {
            csv.println("collection;P;Time_ms;Acceleration");

            for (int f = 0; f < folders.length; f++) {
                System.out.println("=== " + names[f] + " ===");
                System.out.printf("%-4s %-12s %-14s%n", "P", "Time(ms)", "Acceleration");
                System.out.println("-".repeat(34));

                long T1 = 0;

                for (int P = 1; P <= maxP; P++) {
                    long[] times = new long[3];
                    for (int r = 0; r < 3; r++) {
                        times[r] = Experiment.run(folders[f], P);
                    }

                    Arrays.sort(times);
                    long time = times[1];

                    if (P == 1) T1 = time;

                    double speedup = (double) T1 / time;

                    System.out.printf("%-4d  %-12d  %.3f x%n", P, time, speedup);
                    csv.printf("%s;%d;%d;%.4f%n", names[f], P, time, speedup);
                }
                System.out.println();
            }
        }
        System.out.println("Done! Open up results.csv in Excel.");
    }
}