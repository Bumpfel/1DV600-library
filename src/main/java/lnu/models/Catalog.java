package lnu.models;

import lnu.dao.*;

import java.util.ArrayList;
//import java.util.Collections;
import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement(name = "catalog") // instantiates a Catalog of the xml element catalog
public class Catalog {

    // instantiates a Book object of all book elements inside the catalog element and adds them to the ArrayList
    @XmlElement(name = "book")
    private ArrayList<Book> books = new ArrayList<>();

    public Catalog() {
    }

    // Used when testing
    public Catalog(Book... newBooks) {
        for(Book book : newBooks)
            addBook(book);
    }

    public void addBook(Book newBook) {
        books.add(newBook);
        //Collections.sort(books);
    }

    public int size() { // using this to help conducting tests
        return books.size();
    }

    // Returns a copy of the array list, so the original can't be modified.
    public ArrayList<Book> getBooks() {
        ArrayList<Book> copy = new ArrayList<>();
        for(Book bk : books)
            copy.add(bk);
        return copy;
    }

    public String getBooksAsJson() {
        return booksDAO.objToJson(books);
    }

    // Determines a free id number by finding the highest id in the catalog and returns one int higher than that
    public int nextId() {
        int highest = 0;
        for(Book bk : books) {
            int thisId = Integer.parseInt(bk.getId());
            if(thisId > highest)
                highest = thisId;
        }
        return highest + 1;
    }

    // Searches Catalog for duplicates. I count the book as being a duplicate when the title and author are the same
    public boolean containsDuplicate(Book book) {
        for(Book bk : books) {
            if(book.getId() != bk.getId() && book.getTitle().equalsIgnoreCase(bk.getTitle()) && book.getAuthor().equalsIgnoreCase(bk.getAuthor())) {
                System.out.println("---------------------------------------------- Duplicate book found --------------------------------------------------"); // DEBUG
                return true;
            }
        }
        return false;
    }
    
    public Book getBook(String id) {
        Book ret = null;
        for(Book bk : books) {
            if(bk.getId().equals(id))
                ret = bk;
        }
        return ret;
    }

    // searches for a book with the argument id in the ArrayList and removes that book from the ArrayList when found
    public void removeBook(String id) {
        for(int i = 0; i < books.size(); i ++) {
            if(books.get(i).getId().equals(id))
                books.remove(i);
        }
    }

}
