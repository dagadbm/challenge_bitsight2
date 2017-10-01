package com.bitsight.boundary;

import com.bitsight.controller.DependencyManagement;
import com.bitsight.controller.DependencyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new UnsupportedOperationException("Please write the full path to the project file.");
        }

        DependencyManagement dp = new DependencyManager();
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            final String firstLine = br.readLine();
            String[] tokens = firstLine.split(" ");
            final Integer numberOfTasks = Integer.valueOf(tokens[0]);

            dp.init(numberOfTasks);

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    tokens = line.split(" ");
                    final int task = Integer.valueOf(tokens[0]);

                    //task dependencies dep1 dep2 -> 1 2 4 2
                    final List<Integer> dependencies = new ArrayList();
                    for (int i = 2; i < tokens.length; i++) {
                        dependencies.add(Integer.valueOf(tokens[i]));
                    }

                    dp.addAllDependencies(task, dependencies);
                }
            }

            System.out.println(dp.resolve());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
