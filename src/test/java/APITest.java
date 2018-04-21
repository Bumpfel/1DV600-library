import lnu.dao.*;
import lnu.models.*;
import lnu.resources.*;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;

// Look at this to find out how to create an api test.
// Info on how to test with dropwizard: http://www.dropwizard.io/0.8.0/docs/manual/testing.html
public class APITest {
	private Catalog originalCatalog = booksDAO.XMLToJava();
	private String emptyJson = "[]";
	private String actualString, expectedString;
	private int actualResponse, expectedResponse;
	private Book testBook = new Book("some title", "some author", "some genre", "some date", "some price", "some description");
	private Catalog testCatalog = new Catalog(testBook);
	private final String FILE_PATH = "src/test/";

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new GetBooksResource())
		.addResource(new RemoveBookResource())
		.build();

	@Test
	public void testGetBooksReturn() {
		// checks that the api get request returns the same as the backend xml read
		booksDAO.javaToXML(originalCatalog); // writes to the file since the JUnit doesn't seem to guarantee it's executedin order from top to bottom
		expectedString = originalCatalog.getBooksAsJson();
		actualString = resources.client().target("/books").request().get(String.class);
		assertEquals(expectedString, actualString);
		// writes a catalog an empty Catalog to XML, and checks that the get request returns an empty Json string
		// (just to check with different data)
		booksDAO.javaToXML(new Catalog());
		expectedString = emptyJson;
		actualString = resources.client().target("/books").request().get(String.class);
		assertEquals(expectedString, actualString);
	}

	@Test
	public void testGetBooksResponse() {
		// checks that the http response is 200
		booksDAO.javaToXML(originalCatalog);
		expectedResponse = 200;
		actualResponse = resources.client().target("/books").request().get().getStatus();
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testRemoveBook() {
		// writes a catalog with a single book to xml, deletes it and checks the http response...
		booksDAO.javaToXML(new Catalog(testBook));
		actualResponse = resources.client().target("/books/" + testBook.getId()).request().delete().getStatus();
		expectedResponse = 204; // my method to remove book returns void, so response 204 would mean success
		assertEquals(expectedResponse, actualResponse);

		// ...and makes sure its been deleted (checks that the get request returns nothing)
		actualString = resources.client().target("/books/").request().get(String.class);
		expectedString = emptyJson;
		assertEquals(expectedString, actualString);
	}

	@After
	public void teardown() {
		booksDAO.javaToXML(originalCatalog); // restores books.xml to its original state
	}
}
