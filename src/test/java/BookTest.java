import lnu.models.Book;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    private String expectedString, actualString;
    private String title = "test title", author = "test author", genre = "test genre",
    publishDate = "2000-01-01", price = "100", description = "test description";
    private Book sut = new Book(title, author, genre, publishDate, price, description);
    private Book sutEmpty = new Book();

    @Test
    public void shouldPassIfGetTitleReturnsexpectedStringResult() {
        expectedString = title;
        actualString = sut.getTitle();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void shouldPassIfGetTitleReturnsNull() {
        expectedString = sutEmpty.getTitle();
        assertNull(expectedString);
    }
    /*@Test
    public void shouldPassIfNullTitleIsNotValid() {
        boolean expectedFalse = Book.isValidTitle(null);
        assertFalse(expectedFalse);  // The unimplemented method
    }*/

    @Test
    public void testGetAuthor() {
        expectedString = author;
        actualString = sut.getAuthor();
        assertEquals(expectedString, actualString);
        assertNull(sutEmpty.getAuthor());
    }

    @Test
    public void testGetGenre() {
        expectedString = genre;
        actualString = sut.getGenre();
        assertEquals(expectedString, actualString);
        assertNull(sutEmpty.getGenre());
    }

    @Test
    public void testGetPublishDate() {
        expectedString = publishDate;
        actualString = sut.getPublishDate();
        assertEquals(expectedString, actualString);
        assertNull(sutEmpty.getPublishDate());
    }

    @Test
    public void testGetPrice() {
        expectedString = price;
        actualString = sut.getPrice();
        assertEquals(expectedString, actualString);
        assertNull(sutEmpty.getPrice());
    }

    @Test
    public void testGetDescripton() {
        expectedString = description;
        actualString = sut.getDescription();
        assertEquals(expectedString, actualString);
        assertNull(sutEmpty.getDescription());
    }

    @Test
    public void testIsNotEmpty() {
        String testString = "some title";
        assertTrue(Book.isNotEmpty(testString));
    }
    @Test
    public void testIsEmpty() {
        String emptyString = " "; // test method should trim the string
        assertFalse(Book.isNotEmpty(emptyString));
    }

    @Test
    public void testNumericAndPositive() {
        String numericPositive = "900";
        assertTrue(Book.isNumericAndPositive(numericPositive));
    }
    @Test
    public void testNumericNegative() {
        String numericNegative = "-80";
        assertFalse(Book.isNumericAndPositive(numericNegative));
    }
    @Test
    public void testNotNumeric() {
        String notNumeric = "test";
        assertFalse(Book.isNumericAndPositive(notNumeric));
    }

    @Test
    public void testValidDate() {
        String date = "2018-03-18";
        assertTrue(Book.isValidDate(date));
    }
    @Test
    public void testInvalidDate() {
        String date = "2017-02-29"; // not a leap year
        assertFalse(Book.isValidDate(date));
    }
    @Test
    public void testInvalidDateFormat() {
        String date = "18-03-18"; // should be in YYYY-MM-DD
        assertFalse(Book.isValidDate(date));
    }
}
