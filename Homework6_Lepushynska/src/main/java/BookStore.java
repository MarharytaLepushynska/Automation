import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BookStore {
    private List<Book> books;

    public BookStore() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new IllegalArgumentException("Book already exists");
        }
        books.add(book);
    }

    public boolean deleteBook(String title) {
        Book book = findBook(title);
        if (books.contains(book)) {
            books.remove(book);
            return true;
        }
        return false;
    }

    public long receiveBooks(String title, long number) {
        Book book = findBook(title);
        if (book == null) {
            book = new Book(title, number);
            books.add(book);
        } else {
            long newAmount = book.getAmount() + number;
            book.setAmount(newAmount);
        }
        return book.getAmount();
    }

    public long sellBooks(String title, long number) {
        Book book = findBook(title);
        if (book == null) {
            throw new NoSuchElementException("No such book");
        }
        if (book.getAmount() < number) {
            throw new ArithmeticException("Not enough books in store");
        }
        book.setAmount(book.getAmount() - number);
        return book.getAmount();
    }

    public void cleanStore() {
        books = new ArrayList<>();
    }

    private Book findBook(String title) {
        for (Book book : books) {
            if(book.getTitle().equals(title)) {
                return book;
            }
        }
        System.out.println("Book not found");
        return null;
    }
}
