package com.bitsight.entity;

import java.util.HashSet;
import java.util.Set;

public class Task<E extends Comparable> implements Comparable<Task<E>> {

    private E id;
    private Set<Task<E>> dependents;
    private Set<Task<E>> dependencies;
    private boolean visited;

    public Task(E id) {
        dependencies = new HashSet();
        dependents = new HashSet();
        this.id = id;
    }

    public E getId() {
        return id;
    }

    public Set<Task<E>> getDependents() {
        return dependents;
    }

    public void addDependents(Task<E> dependents) {
        this.dependents.add(dependents);
    }

    public Set<Task<E>> getDependencies() {
        return dependencies;
    }

    public void addDependency(Task<E> dependency) {
        this.dependencies.add(dependency);
    }

    public void removeDependency(Task<E> dependency) {
        this.dependencies.remove(dependency);
    }

    public void setDependencies(Set<Task<E>> dependencies) {
        this.dependencies = dependencies;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public int compareTo(Task<E> other) {
        final Integer ownDependencies = Integer.valueOf(this.dependencies.size());
        final Integer otherDependencies = Integer.valueOf(other.dependencies.size());

        final E ownId = this.id;
        final E otherId = other.id;

        return ownDependencies.compareTo(otherDependencies) == 0 ? ownId.compareTo(otherId) : ownDependencies.compareTo(otherDependencies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task<E> task = (Task<E>) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
