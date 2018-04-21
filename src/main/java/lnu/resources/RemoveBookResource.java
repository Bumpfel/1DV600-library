package lnu.resources;

import lnu.models.Catalog;
import lnu.dao.booksDAO;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books/{id}") //the id from the browser path
@Produces(MediaType.APPLICATION_JSON)
public class RemoveBookResource {

    @DELETE
    public void removeBook(@PathParam("id") String id) { // put id from path as parameter
        Catalog cat = booksDAO.XMLToJava(); // read xml file as a Catalog object
        cat.removeBook(id); // remove book from Catalog
        booksDAO.javaToXML(cat); // write remaining ArrayList to XML (overwrite)
    }
}
