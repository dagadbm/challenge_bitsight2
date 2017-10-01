package com.bitsight.controller;

public interface DependencyManager {
    /**
     * Initialize the dependency manager with the total number of tasks to be resolved
     * @param numberOfTasks
     */
    void init(final int numberOfTasks);

    /**
     * Add all the dependencies to a specific task. This method will override previous dependencies of that task.
     * @param task
     * @param dependencies
     */
    void addAllDependencies(final int task, final int... dependencies);

    /**
     * Resolves the dependencies and returns a string with the correct dependency order separated by spaces
     * @return String
     */
    String resolve();
}
