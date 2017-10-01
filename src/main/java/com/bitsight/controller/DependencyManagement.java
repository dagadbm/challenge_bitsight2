package com.bitsight.controller;

import java.util.Collection;

/**
 * Interface to solve dependency management problem
 */
public interface DependencyManagement {
    /**
     * Initialize the dependency manager with the total number of tasks to be resolved
     *
     * @param numberOfTasks
     */
    void init(final Integer numberOfTasks);

    /**
     * Add all the dependencies to a specific task. This method will override previous dependencies of that task.
     *
     * @param task
     * @param dependencies
     */
    void addAllDependencies(final Integer task, final Collection<Integer> dependencies);

    /**
     * Resolves the dependencies and returns a string with the correct dependency resolution order separated by spaces
     *
     * @return String
     */
    String resolve();
}
