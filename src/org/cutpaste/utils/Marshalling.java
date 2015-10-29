package org.cutpaste.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Class with utility methods to marshall/unmarshall between XML and different object types
 * Can either be used with the static methods directly.
 * It can also be instantiated for marshalling/unmarshalling a single class.
 * This is more efficient if many objects need marshalling/unmarshalling.
 *
 * Created by PARY04 on 2015-10-29.
 */

public class Marshalling <T> {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public Marshalling(Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        unmarshaller = jaxbContext.createUnmarshaller();
        marshaller = jaxbContext.createMarshaller();
    }

    public String marshall(T object) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(object, stringWriter);
        return stringWriter.toString();
    }

    public T unmarshall(String xml) throws JAXBException {
        StringReader stringReader = new StringReader(xml);
        return (T) unmarshaller.unmarshal(stringReader);
    }


    public static <T> String marshall(T object, Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(object, stringWriter);
        return stringWriter.toString();
    }

    public static <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader stringReader = new StringReader(xml);
       return (T) unmarshaller.unmarshal(stringReader);
    }

}
