package com.bitsight.entity;

import com.bitsight.controller.DependencyManager;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphImplementation implements DependencyManager {

    private Map<Integer, List<Integer>> adjacencies;
    Set<Integer> resolvedDependencies = new LinkedHashSet();

    public void init(int numberOfTasks) {
        adjacencies = new HashMap<>(numberOfTasks);

        // initialize all the edges
        adjacencies.putAll(IntStream.rangeClosed(0, numberOfTasks)
                .boxed()
                .collect(Collectors.toMap(Integer::valueOf, ArrayList::new)));

        // make a root edge with all the dependency edges sorted
        adjacencies.put(0, IntStream.rangeClosed(1, numberOfTasks)
                .boxed()
                .collect(Collectors.toList()));
    }

    public void addAllDependencies(int task, int... dependencies) {
        adjacencies.put(task, IntStream.of(dependencies)
                                       .boxed()
                                       .sorted() //lower node numbers first
                                       .collect(Collectors.toList()));
    }

    public String resolve() {
        visit(Integer.valueOf(0));
        return resolvedDependencies.stream()
                       .map(String::valueOf)
                       .collect(Collectors.joining(" "));
    }

    private void visit(Integer node) {
        if(canResolve(node)) {
            resolvedDependencies.add(node);
        }

        for(Integer e : adjacencies.get(node)) {
            if(!isResolved(e)) {
                visit(e);
            }
        }

        if(canResolve(node)) {
            resolvedDependencies.add(node);
        }
    }

    private boolean canResolve(Integer node) {
        if(isResolved(node) || node == Integer.valueOf(0)) {
            return false;
        }

        if(adjacencies.get(node) == null || isResolved(adjacencies.get(node))) {
            return true;
        }
        return false;
    }

    private boolean isResolved(Integer node) {
        return resolvedDependencies.contains(node);
    }

    private boolean isResolved(List<Integer> nodes) {
        return nodes.stream()
                    .allMatch(p -> resolvedDependencies.contains(p));
    }
}

