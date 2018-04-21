package lnu.models;

import lnu.dao.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "book")
public class Book {


    @XmlAttribute(name="id")
    private String id;
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "author")
    private String author;
    @XmlElement(name = "genre")
    private String genre;
    @XmlElement(name = "publish_date")
    private String publishDate;
    @XmlElement(name = "price")
    private String price;
    @XmlElement(name = "description")
    private String description;

    public Book() {
    }

    // Used when conducting tests
    private static int nextID = 1;
    public Book(String newTitle, String newAuthor, String newGenre, String newPublishDate, String newPrice, String newDescription) {
        id = nextID + "";
        title = newTitle;
        author = newAuthor;
        genre = newGenre;

        publishDate = newPublishDate;
        price = newPrice;
        description = newDescription;

        nextID ++;
    }

    public void setBookId(String newId) { id = newId; }

    public String toJson() { // handy
        return booksDAO.objToJson(this);
    }

    // Input checks
    public boolean hasValidInputs() {
        if(isNotEmpty(title) && isNotEmpty(author) && isNotEmpty(genre) && isValidDate(publishDate) && isNumericAndPositive(price) && isNotEmpty(description))
            return true;
        return false;
    }

    public static boolean isNotEmpty(String str) {
        if(str.trim().length() > 0)
            return true;
        System.out.println("---------------------------------------------- A field was empty --------------------------------------------------"); // DEBUG
        return false;
    }

    public static boolean isNumericAndPositive(String str) {
        try {
            if(Double.parseDouble(str) >= 0)
                return true;
            System.out.println("---------------------------------------------- Price entered is not positive --------------------------------------------------"); // DEBUG
            return false;
        }
        catch(NumberFormatException e) {
            System.out.println("---------------------------------------------- Price entered is not numeric --------------------------------------------------"); // DEBUG
            return false;
        }
    }

    public static boolean isValidDate(String str) {
        try {
            if(str.length() == 10) { // checks valid format (length)
                char dash1 = str.charAt(4);
                char dash2 = str.charAt(7);
                if(dash1 == '-' && dash2 == '-') { // checks valid format

                    String yearStr = str.substring(0, 4);
                    String monthStr = str.substring(5, 7);
                    String dayStr = str.substring(8, 10);

                    int year = Integer.parseInt(yearStr);
                    int month = Integer.parseInt(monthStr);
                    int day = Integer.parseInt(dayStr);

                    if(year >= 0 && month > 0 && month <= 12 && day > 0) { // checks valid year and month
                        // checks valid day
                        if(day <= 28)
                            return true;
                        else if(year % 4 == 0 && day == 29)
                            return true;
                        else if(day <= 30 && (month == 4 || month == 6 || month == 9 || month == 11))
                            return true;
                        else if(day <= 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12))
                            return true;
                    }
                }
            }
            System.out.println("---------------------------------------------- Illegal date format or date does not exist --------------------------------------------------"); // DEBUG
            return false;
        }
        catch(NumberFormatException e) {
            System.out.println("-------------------------------------------------- Month, year, or day was not numeric -----------------------------------------------------"); // DEBUG
            return false;
        }
    }

    // Getters
    public String getId() { return id; }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public String getGenre() { return genre; }

    public String getPublishDate() { return publishDate; }

    public String getPrice() { return price; }

    public String getDescription() { return description; }
}
