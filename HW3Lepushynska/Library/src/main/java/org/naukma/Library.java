package org.naukma;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a library with list of books
 */
public class Library {
    private List<String> books = new ArrayList<String>();

    //TODO: add sort by name

    /**
     * Adds a book to the library
     * @param book
     */
    public void addBook(String book) {
        books.add(book);
    }

    /**
     * Returns all books in the library
     */
    public List<String> getBooks() {
        return books;
    }

    public void addBooks(File file){
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                books.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
