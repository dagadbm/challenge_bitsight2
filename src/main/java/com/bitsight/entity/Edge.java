package com.bitsight.entity;

public class Edge {

    private int value;
    private boolean visited;

    public Edge(int value) {
        this.value = value;
    }

    public void visit() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public int getValue() {
        return value;
    }
}