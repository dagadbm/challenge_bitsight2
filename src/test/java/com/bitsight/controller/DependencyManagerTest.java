package com.bitsight.controller;

import com.bitsight.entity.GraphImplementation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by David on 30/09/2017.
 */
public class DependencyManagerTest {

    DependencyManager underTest;

    @Before
    public void setUp() {
        underTest = new GraphImplementation();
    }

    @Test
    public void exercise() {
        underTest.init(5);

        underTest.addAllDependencies(3, 1,5);
        underTest.addAllDependencies(2, 5,3);
        underTest.addAllDependencies(4, 3);
        underTest.addAllDependencies(5,1);

        final String result = underTest.resolve();
        assertEquals("1 5 3 2 4",result );
    }

    @Test
    public void sortedEdgeCase() {
        underTest.init(5);

        underTest.addAllDependencies(2,5,3);
        underTest.addAllDependencies(3,1,4);
        underTest.addAllDependencies(4,1);

        final String result = underTest.resolve();
        assertEquals("1 4 3 5 2",result);
    }
}
