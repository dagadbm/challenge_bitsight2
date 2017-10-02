package com.bitsight.controller;

import com.bitsight.entity.Task;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implements DependencyManagement interface using khan's algorithm for topological sorting with a priority queue
 */
public class DependencyManager implements DependencyManagement {

    /**
     * Tasks to resolve dependencies
     */
    private Map<Integer, Task<Integer>> tasks;
    /**
     * Ordered resolved dependencies by insertion
     */
    Set<Task<Integer>> resolvedDependencies = new LinkedHashSet();

    public void init(Integer numberOfTasks) {
        tasks = IntStream.rangeClosed(1, numberOfTasks)
                         .boxed()
                         .collect(Collectors.toMap(Function.identity(), Task::new));
    }

    public void addAllDependencies(Integer task, Collection<Integer> dependencies) {
        final Task<Integer> currentTask = tasks.get(task);
        final Set<Task<Integer>> dependencyList = dependencies.stream()
                                                              .map(Task::new)
                                                              .collect(Collectors.toSet());
        if (currentTask != null) {
            currentTask.setDependencies(dependencyList);

            // update dependents
            for (Task<Integer> dependency : dependencyList) {
                tasks.get(dependency.getId()).addDependents(currentTask);
            }
        }
    }

    public String resolve() {
        traverse();
        return resolvedDependencies.stream()
                                   .map(String::valueOf)
                                   .collect(Collectors.joining(" "));
    }

    private void traverse() {
        final PriorityQueue<Task<Integer>> traverseQueue = createTraverseQueue();

        while (resolvedDependencies.size() != tasks.size()) {
            Task<Integer> currentTask = traverseQueue.poll();

            if (currentTask != null && !resolvedDependencies.contains(currentTask)) {
                resolvedDependencies.add(currentTask);

                // update dependencies for other tasks that depend on currentTask
                for (Task<Integer> dependent : currentTask.getDependents()) {
                    dependent.removeDependency(currentTask);
                    // push changed task into the queue for further traversing if it has no more dependencies
                    if (dependent.getDependencies().isEmpty()) {
                        traverseQueue.offer(dependent);
                    }
                }
            }
        }
    }

    /**
     * Create a priority queue to khanTraverse with the tasks that have 0 dependencies. i.e. tasks that can be resolved.
     */
    private PriorityQueue<Task<Integer>> createTraverseQueue() {
        final PriorityQueue<Task<Integer>> traverseQueue = new PriorityQueue();
        traverseQueue.addAll(tasks.values().stream()
                                  .filter(p -> p.getDependencies().isEmpty())
                                  .collect(Collectors.toList()));
        return traverseQueue;
    }
}

