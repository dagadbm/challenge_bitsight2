package com.bitsight.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskTest {

    @Test
    public void differentIdSameDependency() throws Exception {
        Task<Integer> task1 = new Task(1);
        Task<Integer> task2 = new Task(2);
        task1.addDependency(new Task(4));
        task2.addDependency(new Task(6));
        assertEquals(true, task1.compareTo(task2) < 0);
    }

    @Test
    public void differentIdDifferentDependency() throws Exception {
        Task<Integer> task1 = new Task(1);
        Task<Integer> task2 = new Task(2);
        task1.addDependency(new Task(123));
        assertEquals(true, task1.compareTo(task2) > 0);
    }

    @Test
    public void sameIdDifferentDependency() throws Exception {
        Task<Integer> task1 = new Task(1);
        Task<Integer> task2 = new Task(1);
        task2.addDependency(new Task(123));
        assertEquals(true, task1.compareTo(task2) < 0);
    }

    @Test
    public void sameIdSameDependency() throws Exception {
        Task<Integer> task1 = new Task(1);
        Task<Integer> task2 = new Task(1);
        assertEquals(true, task1.compareTo(task2) == 0);
    }
}