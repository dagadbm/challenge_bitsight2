package com.bitsight.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DependencyManagerTest {

    DependencyManagement underTest;

    @Before
    public void setUp() {
        underTest = new DependencyManager();
    }

    @Test
    public void exercise() {
        underTest.init(5);

        underTest.addAllDependencies(3, Arrays.asList(1, 5));
        underTest.addAllDependencies(2, Arrays.asList(5, 3));
        underTest.addAllDependencies(4, Arrays.asList(3));
        underTest.addAllDependencies(5, Arrays.asList(1));

        final String result = underTest.resolve();
        assertEquals("1 5 3 2 4", result);
    }

    @Test
    public void noDependencies() {
        underTest.init(15);

        final String result = underTest.resolve();
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15", result);
    }

    @Test
    public void noTasks() {
        underTest.init(0);

        final String result = underTest.resolve();
        assertEquals("", result);
    }

    @Test
    public void testStackOverflow() {
        final int tasks = 5000;
        underTest.init(tasks);

        for (int i = 2; i < tasks; i++) {
            underTest.addAllDependencies(i, Arrays.asList(i + 1));
        }

        underTest.resolve();
    }

    @Test
    public void sortedEdgeCase() {
        underTest.init(5);

        underTest.addAllDependencies(2, Arrays.asList(5, 3));
        underTest.addAllDependencies(3, Arrays.asList(1, 4));
        underTest.addAllDependencies(4, Arrays.asList(1));

        final String result = underTest.resolve();
        assertEquals("1 4 3 5 2", result);
    }

    @Test
    public void simpleCase() {
        underTest.init(3);

        underTest.addAllDependencies(1, Arrays.asList(2));
        underTest.addAllDependencies(3, Arrays.asList(1));

        final String result = underTest.resolve();
        assertEquals("2 1 3", result);
    }

    @Test
    public void firstTaskDependsOnLast() {
        underTest.init(5);

        underTest.addAllDependencies(1, Arrays.asList(5));
        underTest.addAllDependencies(2, Arrays.asList(4, 1));
        underTest.addAllDependencies(3, Arrays.asList(2));

        final String result = underTest.resolve();
        assertEquals("4 5 1 2 3", result);
    }
}

