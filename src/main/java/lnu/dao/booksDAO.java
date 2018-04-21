package lnu.dao;

import lnu.models.Catalog;
import lnu.models.Book;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class booksDAO {
    private static final String FILE_PATH = "books.xml";

    public static Catalog XMLToJava() {
        return XMLReader(FILE_PATH);
    }

    public static Catalog XMLToJava(String file) {
        return XMLReader(file);
    }

    private static Catalog XMLReader(String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Catalog.class);
            Unmarshaller un = context.createUnmarshaller();
            return (Catalog) un.unmarshal(new File(file));
        }
        catch(JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void javaToXML(Catalog cat) {
        try {
            JAXBContext context = JAXBContext.newInstance(Catalog.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(cat, new File(FILE_PATH));
        }
        catch(JAXBException e) {
            e.printStackTrace();
        }
    }

    // used in testing
    public static void javaToXML(Catalog cat, String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Catalog.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(cat, new File(file));
        }
        catch(JAXBException e) {
            e.printStackTrace();
        }
    }

    public static String objToJson(Object obj) {
        String jsonString = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(obj);
        }
        catch(JsonProcessingException ex) {
            System.out.print(ex.getMessage());
        }
        return jsonString;
    }

    public static Book jsonToBook(String jsonString) {
        Book bk = null;
        try {
            bk = new ObjectMapper().readValue(jsonString, Book.class);
        }
        catch (JsonGenerationException e) {
   			e.printStackTrace();
   		}
        catch (JsonMappingException e) {
   			e.printStackTrace();
        }
        catch (IOException e) {
   			e.printStackTrace();
   		}
        return bk;
    }
}
