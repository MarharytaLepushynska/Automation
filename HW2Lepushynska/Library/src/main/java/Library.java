import java.util.ArrayList;
import java.util.List;

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
}
