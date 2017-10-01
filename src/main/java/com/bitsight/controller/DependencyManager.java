package com.bitsight.controller;
import java.util.List;

public interface DependencyManager {
    void init(final int numberOfTasks);
    void addAllDependencies(final int task, final int... dependencies);
    String resolve();
}
