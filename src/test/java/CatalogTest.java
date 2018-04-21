import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lnu.dao.*;
import lnu.models.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class CatalogTest {
    private String expectedString, actualString;
    private String emptyJson = "[]";
    private Book book1 = new Book("title", "author", "genre", "2000-01-01", "100", "description");
    private Book book2 = new Book("title2", "author2", "genre2", "2000-01-02", "200", "description2");
    private Book duplicateBook = new Book("Title", "AuTHor", "some other genre", "1999-12-31", "999", "some other description");
    private Catalog sut = new Catalog(book1, book2);
    private Catalog emptyCat = new Catalog();

    /*private void restoreSut() {
        sut = new Catalog(book1, book2);
    }*/

    @Test
    public void testGetBooksAsJson() {
        // testing that the return is an empty Json string
        expectedString = emptyJson;
        actualString = emptyCat.getBooksAsJson();
        assertEquals(emptyJson, actualString);

        // testing that the method returns a correct Json string
        expectedString = "[" + book1.toJson() + "," + book2.toJson() + "]";
        actualString = sut.getBooksAsJson();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testRemoveBook() {
        // testing that book1 is removed from the Catalog, and that book2 remains
        sut.removeBook(book1.getId());
        expectedString = "[" + book2.toJson() + "]";
        actualString = sut.getBooksAsJson();
        assertEquals(expectedString, actualString);

        sut = new Catalog(book1, book2); // restore sut
        expectedString = sut.getBooksAsJson();
        sut.removeBook("-1");
        actualString = sut.getBooksAsJson();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testAddBook() {
        Book book1 = new Book("title", "author", "genre", "2000-01-01", "100", "description");
        // Tests that an object is added to the Catalog.
        Catalog cat = new Catalog(); // empty catalog
        //int initialSize = cat.size();

        int expected = 1;
        cat.addBook(book1);
        int actual = cat.size();
        //assertEquals(initialSize, actual); // simply making sure catalog start with size 0
        assertEquals(expected, actual); // tests that the catalog increases in size

        ArrayList books = cat.getBooks();

        Book expectedBook = book1;
        Book actualBook = (Book) books.get(books.size() - 1);
        // no idea why I had to cast it to Book, but it didn't work otherwise

        assertSame(expectedBook, actualBook); // Testing that it was book1 that was added
    }

    @Test
    public void testNextId() {
        Catalog testCat = booksDAO.XMLToJava("src/test/xml/multiple_books.xml"); // the highest id in this file is 9
        int expected = 10;

        int actual = testCat.nextId();
        assertEquals(expected, actual);
    }

    @Test
    public void testContainsDuplicate() {
        // tests that the method returns true when there's a book with the same title and author in the catalog
        Book testBook = new Book("test", "test", "test", "1000-01-01", "111", "test");
        assertTrue(sut.containsDuplicate(duplicateBook));
        assertFalse(sut.containsDuplicate(testBook));
    }

    @Test
    public void testGetBook() {
        // tests that the right object is fetched
        String bookId = book1.getId();
        Book testBook = sut.getBook(bookId);

        assertSame(book1, testBook);
    }


}
