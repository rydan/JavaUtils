package org.cutpaste.utils;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class MarshallingTest {

    @Test
    public void testMarshalUnmarshal() throws JAXBException {
        MarshallerTestClass testingTesting = new MarshallerTestClass("TestingTesting", 12345);
        String marshall = Marshalling.marshall(testingTesting, MarshallerTestClass.class);
        MarshallerTestClass result = Marshalling.unmarshal(marshall, MarshallerTestClass.class);
        assertNotSame(testingTesting, result);
        assertEquals(testingTesting, result);
    }

    @Test
    public void testInstanceMarshalUnmarshal() throws JAXBException {
        MarshallerTestClass testingTesting = new MarshallerTestClass("TestingTesting", 12345);
        Marshalling<MarshallerTestClass> marshallerTestClassMarshalling = new Marshalling<>(MarshallerTestClass.class);
        String marshall = marshallerTestClassMarshalling.marshall(testingTesting);
        MarshallerTestClass result = marshallerTestClassMarshalling.unmarshall(marshall);
        assertNotSame(testingTesting, result);
        assertEquals(testingTesting, result);
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

}