package com.bitsight.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphDependencyManager implements DependencyManager {

    /**
     * Grap representation of the dependencies
     */
    private Map<Integer, List<Integer>> adjacencies;
    private static final Integer DUMMY_ROOT = Integer.valueOf(0);

    /**
     * Resolved dependencies by insertion order. Resolved dependencies are nodes that have been visited and contain
     * no dependencies
     */
    Set<Integer> resolvedDependencies = new LinkedHashSet();

    public void init(int numberOfTasks) {
        adjacencies = new HashMap<>(numberOfTasks);

        // initialise all tasks
        adjacencies.putAll(IntStream.rangeClosed(0, numberOfTasks)
                .boxed()
                .collect(Collectors.toMap(Integer::valueOf, ArrayList::new)));

        // create dummy root node with all tasks as edges for traversal
        adjacencies.put(0, IntStream.rangeClosed(1, numberOfTasks)
                .boxed()
                .collect(Collectors.toList()));
    }

    public void addAllDependencies(int task, int... dependencies) {
        adjacencies.put(task, IntStream.of(dependencies)
                .boxed()
                .sorted() //lower node numbers first for correct priority traversal
                .collect(Collectors.toList()));
    }

    public String resolve() {
        traverse(DUMMY_ROOT);
        return resolvedDependencies.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    private void traverse(Integer node) {
        final List<Integer> children = adjacencies.get(node);
        // stopping condition
        if (isResolved(node)) {
            return;
        } else if (children == null) {
            return;
        } else if (isResolved(children)) {
            resolvedDependencies.add(node);
            return;
        }

        // traverse all children
        for (Integer e : children) {
            if (!isResolved(e)) {
                traverse(e);
            }
        }

        // check if there were changes in the nodes children after traversing (ignore dummy root)
        if (!isDummyRoot(node) && isResolved(children)) {
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

    private boolean isDummyRoot(Integer parent) {
        return parent == DUMMY_ROOT;
    }
}

