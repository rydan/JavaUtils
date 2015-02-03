package org.cutpaste.utils;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoNullTest {

    @Test
    public void testGet() {

        TestA a = new TestA();
        Optional<String> value1 = NoNull.get(() -> a.getTestString());
        assertFalse(value1.isPresent());

        TestB testB = new TestB();
        Optional<String> value2 = NoNull.get(() -> testB.getTestA().getTestString());
        //Internal NullPointer
        assertFalse(value2.isPresent());

        testB.setTestA(a);
        Optional<String> value3 = NoNull.get(() -> testB.getTestA().getTestString());
        //Fetched null
        assertFalse(value3.isPresent());

        a.setTestString("TestString");
        Optional<String> value4 = NoNull.get(() -> testB.getTestA().getTestString());
        //Fetched the value
        assertTrue(value4.isPresent());
        assertEquals("TestString", value4.get());
    }

    private static final class TestA {
        String testString;

        public String getTestString() {
            return testString;
        }

        public void setTestString(String testString) {
            this.testString = testString;
        }
    }

    private static final class TestB {
        TestA testA;

        public TestA getTestA() {
            return testA;
        }

        public void setTestA(TestA testA) {
            this.testA = testA;
        }
    }

}