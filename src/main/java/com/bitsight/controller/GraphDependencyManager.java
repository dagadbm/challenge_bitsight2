package com.bitsight.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implements DependencyManager interface using Graphs and DFS like traversing as the solution
 */
public class GraphDependencyManager implements DependencyManager {

    /**
     * Grap representation of the dependencies
     */
    private List<Integer> adjacencies[];
    private static final Integer DUMMY_ROOT = Integer.valueOf(0);

    /**
     * Resolved dependencies by insertion order. Resolved dependencies are nodes that have been visited and contain
     * no dependencies
     */
    Set<Integer> resolvedDependencies = new LinkedHashSet();

    public void init(int numberOfTasks) {
        adjacencies = new ArrayList[numberOfTasks + 1];

        // create dummy root node with all tasks as edges for traversal
        adjacencies[0] = IntStream.rangeClosed(1, numberOfTasks)
                                  .boxed()
                                  .collect(Collectors.toList());
    }

    public void addAllDependencies(int task, int... dependencies) {
        adjacencies[task] = IntStream.of(dependencies)
                                     .boxed()
                                     .sorted() //lower node numbers first for correct priority traversal
                                     .collect(Collectors.toList());
    }

    public String resolve() {
        traverse(DUMMY_ROOT);
        return resolvedDependencies.stream()
                                   .filter(node -> node != DUMMY_ROOT)
                                   .map(String::valueOf)
                                   .collect(Collectors.joining(" "));
    }

    private void traverse(Integer node) {
        final List<Integer> children = this.adjacencies[node];
        // stopping condition
        if (isResolved(node)) {
            return;
        } else if (children == null || isResolved(children)) {
            resolvedDependencies.add(node);
            return;
        }

        // traverse all children
        for (Integer e : children) {
            if (!isResolved(e)) {
                traverse(e);
            }
        }

        // check if there were changes in the nodes adjacencies after traversing (ignore dummy root)
        if (isResolved(children)) {
            resolvedDependencies.add(node);
        }
    }

    private boolean isResolved(Integer parent) {
        return resolvedDependencies.contains(parent);
    }

    private boolean isResolved(List<Integer> children) {
        //using streams here would give a huge overflow to the call stack.
        for (Integer child : children) {
            if (!isResolved(child)) {
                return false;
            }
        }
        return true;
    }
}

