package org.cutpaste.utils;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class MarshallingTest {

    @Test
    public void testMarshalling() throws JAXBException {
        MarshallerTestClass testingTesting = new MarshallerTestClass("TestingTesting", 12345);
        String marshall = Marshalling.marshall(testingTesting, MarshallerTestClass.class);
        MarshallerTestClass result = Marshalling.unmarshal(marshall, MarshallerTestClass.class);
        assertNotSame(testingTesting, result);
        assertEquals(testingTesting, result);
        assertEquals(1, Marshalling.marshallers.size());
        assertEquals(1, Marshalling.unmarshallers.size());

        MarshallerTestClass2 testingTesting2 = new MarshallerTestClass2("TestingTesting2", 2345);
        String marshall2 = Marshalling.marshall(testingTesting2, MarshallerTestClass2.class);
        MarshallerTestClass2 result2 = Marshalling.unmarshal(marshall2, MarshallerTestClass2.class);
        assertNotSame(testingTesting2, result2);
        assertEquals(testingTesting2, result2);
        assertEquals(2, Marshalling.marshallers.size());
        assertEquals(2, Marshalling.unmarshallers.size());

        marshall = Marshalling.marshall(testingTesting, MarshallerTestClass.class);
        result = Marshalling.unmarshal(marshall, MarshallerTestClass.class);
        assertNotSame(testingTesting, result);
        assertEquals(testingTesting, result);
        assertEquals(2, Marshalling.marshallers.size());
        assertEquals(2, Marshalling.unmarshallers.size());

        Marshalling.clearMarshallerCache();
        assertEquals(0, Marshalling.marshallers.size());
        assertEquals(0, Marshalling.unmarshallers.size());

        marshall = Marshalling.marshall(testingTesting, MarshallerTestClass.class);
        result = Marshalling.unmarshal(marshall, MarshallerTestClass.class);
        assertNotSame(testingTesting, result);
        assertEquals(testingTesting, result);
        assertEquals(1, Marshalling.marshallers.size());
        assertEquals(1, Marshalling.unmarshallers.size());
    }

    @XmlRootElement
    private static final class MarshallerTestClass {
        private String testString;
        private Integer testInt;

        public MarshallerTestClass() {
        }

        public MarshallerTestClass(String testString, Integer testInt) {
            this.testString = testString;
            this.testInt = testInt;
        }

        public String getTestString() {
            return testString;
        }

        public Integer getTestInt() {
            return testInt;
        }

        public void setTestString(String testString) {
            this.testString = testString;
        }

        public void setTestInt(Integer testInt) {
            this.testInt = testInt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MarshallerTestClass that = (MarshallerTestClass) o;

            if (testString != null ? !testString.equals(that.testString) : that.testString != null) return false;
            return !(testInt != null ? !testInt.equals(that.testInt) : that.testInt != null);

        }

        @Override
        public int hashCode() {
            int result = testString != null ? testString.hashCode() : 0;
            result = 31 * result + (testInt != null ? testInt.hashCode() : 0);
            return result;
        }
    }

    @XmlRootElement
    private static final class MarshallerTestClass2 {
        private String testString;
        private Integer testInt;

        public MarshallerTestClass2() {
        }

        public MarshallerTestClass2(String testString, Integer testInt) {
            this.testString = testString;
            this.testInt = testInt;
        }

        public String getTestString() {
            return testString;
        }

        public Integer getTestInt() {
            return testInt;
        }

        public void setTestString(String testString) {
            this.testString = testString;
        }

        public void setTestInt(Integer testInt) {
            this.testInt = testInt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MarshallerTestClass2 that = (MarshallerTestClass2) o;

            if (testString != null ? !testString.equals(that.testString) : that.testString != null) return false;
            return !(testInt != null ? !testInt.equals(that.testInt) : that.testInt != null);

        }

        @Override
        public int hashCode() {
            int result = testString != null ? testString.hashCode() : 0;
            result = 31 * result + (testInt != null ? testInt.hashCode() : 0);
            return result;
        }
    }

}
