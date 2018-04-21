import lnu.dao.*;
import lnu.models.*;
import lnu.resources.*;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.MediaType;

// Look at this to find out how to create an api test.
// Info on how to test with dropwizard: http://www.dropwizard.io/0.8.0/docs/manual/testing.html
public class EditBookResourceTest {
    private final String TEST_FOLDER = "src/test/";

    private Catalog originalCatalog = booksDAO.XMLToJava();

    private Catalog testCatalog = booksDAO.XMLToJava(TEST_FOLDER + "xml/multiple_books.xml");
    private final String testCatalogString = testCatalog.getBooksAsJson();

	private String actualString, expectedString;

    private Book testBook = new Book("some title", "some author", "some genre", "2000-01-01", "0", "some description");
    private String testBookId = "1"; // the test xml contains a book with id 1 - it's the one we're changing
    private String testBookData;

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new EditBookResource())
		.build();

    private Catalog getEditedCatalog() {
        resources.client().target("/books/" + testBookId).request().post(Entity.json(testBookData));
        booksDAO.javaToXML(booksDAO.XMLToJava(), TEST_FOLDER + "temp.xml"); //  DEBUG
        return booksDAO.XMLToJava();
    }

    @Before
    public void setup() {
        testBook.setBookId(testBookId);
        testBookData = testBook.toJson();
    }

    @Test
    public void testThatEditingChangesXMLFile() {
        booksDAO.javaToXML(testCatalog);
        // checks that the xml is changed after a POST request has been made by comparing json strings before and after
        String currentCatalogString = getEditedCatalog().getBooksAsJson();
        assertNotEquals(testCatalogString, currentCatalogString);
    }

    @Test
    public void testThatEditingChangesBook() {
        booksDAO.javaToXML(testCatalog);
        // checks that the testBook data is equal to the data of the book with the same id in the xml file
        String expectedBookString = testBookData;
        //throw new Exception("Id is: " + testBookId);

        Book editedBook = getEditedCatalog().getBook(testBookId);
        String actualBookString = editedBook.toJson();

        assertEquals(expectedBookString, actualBookString);
    }

    @Test
    public void testThatEditingDoesntCorruptXMLFile() {
        // tests that the data of a book of a certain id is not changed after editing a different book
        booksDAO.javaToXML(testCatalog);

        String bookId = "2";
        Book someBook = testCatalog.getBook(bookId);
        String expectedString = someBook.toJson();

        Catalog editedCatalog = getEditedCatalog();
        Book sameBook = editedCatalog.getBook(bookId);
        String actualString = sameBook.toJson();

        assertEquals(expectedString, actualString);
    }

    /*@Test
    public void testEditBookMock() {
    // tests that all fields have been written to XML, but that the book keeps its ID.
        Catalog orgCat = booksDAO.XMLToJava(FILE_PATH + "xml/one_book");
        String bookId = "1";
        Book bk = orgCat.getBook(bookId);

        String jsonTestBook = booksDAO.objToJson(testBook);
        //String bkJson = booksDAO.objToJson(bk);
        new EditBookResource().editBook(bookId, jsonTestBook, FILE_PATH + "test.xml"); // mimics a post request. replaces bk data with testBook data and writes to a mock xml file

        Catalog moddedCat = booksDAO.XMLToJava(FILE_PATH + "test.xml"); // fetches test xml to make sure the file has been updated
        Book moddedBk = moddedCat.getBook(bookId);
        String moddedBkString = booksDAO.objToJson(moddedBk);

        assertEquals(jsonTestBook, moddedBkString);
    }*/

    @After
    public void teardown() {
        booksDAO.javaToXML(originalCatalog); // restores books.xml to its original state
    }
}
