package org.cutpaste.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class with utility methods to marshall/unmarshall between XML and different object types
 * marshallers are cached, to prevent frequent expensive creation...
 */

public class Marshalling <T> {

    protected static Map<Class, Marshaller> marshallers = new HashMap<>();
    protected static Map<Class, Unmarshaller> unmarshallers = new HashMap<>();


    public static <T> String marshall(T object, Class<T> clazz) throws JAXBException {
        Marshaller marshaller = marshallers.get(clazz);
        if (null == marshaller) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            marshaller = jaxbContext.createMarshaller();
            marshallers.put(clazz, marshaller);
        }
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(object, stringWriter);
        return stringWriter.toString();
    }

    public static <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException {
        Unmarshaller unmarshaller = unmarshallers.get(clazz);
        if (null == unmarshaller) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            unmarshaller = jaxbContext.createUnmarshaller();
            unmarshallers.put(clazz, unmarshaller);
        }
        StringReader stringReader = new StringReader(xml);
        return unmarshaller.unmarshal(new StreamSource(stringReader), clazz).getValue();
    }

    public static void clearMarshallerCache() {
        marshallers = new HashMap<>();
        unmarshallers = new HashMap<>();
    }

}
