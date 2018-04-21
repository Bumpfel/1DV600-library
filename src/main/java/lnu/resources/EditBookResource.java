package lnu.resources;

import lnu.models.*;
import lnu.dao.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/books/{id}")
public class EditBookResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void editBook(@PathParam("id") String id, String jsonString) { //, String file
        // Using the functionality of add and remove instead of changing each field in the object.
        Catalog cat = booksDAO.XMLToJava(); // fetching from XML
        cat.removeBook(id); // removing old book

        // making a new book and setting it to the same id.
        jsonString = jsonString.replaceAll("publish_date", "publishDate"); // fixing format to match java variables
        Book bk = booksDAO.jsonToBook(jsonString);
        bk.setBookId(id);
        cat.addBook(bk); // adding the new book to the catalog

        if(bk.hasValidInputs() && !cat.containsDuplicate(bk))
            booksDAO.javaToXML(cat); // writing to XML
    }
}
