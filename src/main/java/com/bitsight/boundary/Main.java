package com.bitsight.boundary;

import com.bitsight.controller.DependencyManager;
import com.bitsight.controller.GraphDependencyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please write the full path to the project file.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            final String firstLine = br.readLine();

            DependencyManager dp = new GraphDependencyManager();
            dp.init(Integer.valueOf(firstLine.split(" ")[0]));

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    final String[] tokens = line.split(" ");
                    final int task = Integer.valueOf(tokens[0]);

                    //task dependencies dep1 dep2 -> 1 2 4 2
                    final List<Integer> dependencies = new ArrayList();
                    for (int i = 2; i < tokens.length; i++) {
                        dependencies.add(Integer.valueOf(tokens[i]));
                    }

                    dp.addAllDependencies(task, dependencies.stream()
                                                            .mapToInt(Integer::intValue)
                                                            .toArray());
                }
            }

            System.out.println(dp.resolve());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
