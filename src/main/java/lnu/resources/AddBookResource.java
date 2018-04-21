package lnu.resources;

import lnu.models.*;
import lnu.dao.*;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class AddBookResource {

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(String jsonString) {
        Catalog cat = booksDAO.XMLToJava(); // reads current xml content

        jsonString = jsonString.replaceAll("publish_date", "publishDate"); // fixing format

        Book newBook = booksDAO.jsonToBook(jsonString); // creates a new book object of the json string
        newBook.setBookId("" + cat.nextId()); // gets and sets a unique id for the book
        cat.addBook(newBook); // adds book to catalog

        if(newBook.hasValidInputs() && !cat.containsDuplicate(newBook))
            booksDAO.javaToXML(cat); // Writes catalog to XML
    }
}
