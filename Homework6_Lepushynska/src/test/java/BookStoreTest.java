import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BookStoreTest {
    BookStore bookStore = new BookStore();

    @Test
    @Tag("receive")
    void recieveBooksShouldReturnCorrectSum() {
        assertEquals(14L, bookStore.receiveBooks("Sherlock Holmes", 8));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Karaval", "Throne of glass", "Clean Code"})
    @Tag("delete")
    void shouldReturnTrueAfterDeleteBook(String bookName) {
        Book book = new Book(bookName, 6);
        bookStore.addBook(book);

        assertTrue(bookStore.deleteBook(bookName));
    }

    @ParameterizedTest
    @CsvSource({
            "Karaval, 4, 6",
            "Throne of glass, 3, 9",
            "Clean Code, 7, 8"
    })
    @Tag("sell")
    void sellBooksShouldReturnCorrectSum(String title, long number, long expected) {
        assertEquals(expected, bookStore.sellBooks(title, number));
    }

    @TestFactory
    @Tag("receive")
    List<DynamicTest> dynamicReceiveTests() {

        List<String> titles = List.of(
                "Karaval", "Throne of glass", "Clean Code"
        );

        List<Long> numbers = List.of(10L, 5L, 80L);
        List<Long> expected = List.of(20L, 17L, 95L);

        List<DynamicTest> tests = new ArrayList<>();
        for(int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            long number = numbers.get(i);
            long expect = expected.get(i);

            tests.add(
                    DynamicTest.dynamicTest(
                            "Receive " + number + " of " + title,
                            () -> assertEquals(expect, bookStore.receiveBooks(title, number))
                    )
            );
        }

        return tests;
    }

    @Test
    @Tag("sell")
    void sellBookOnlyIfStoreContainsIt() {
        assumeTrue(bookStore.sellBooks("Karaval", 0) >= 0);

        assertEquals(8, bookStore.sellBooks("Karaval", 2));
    }

    @BeforeEach
    void fillStore() {
        bookStore.cleanStore();
        bookStore.addBook(new Book("Sherlock Holmes", 6));
        bookStore.addBook(new Book("Karaval", 10));
        bookStore.addBook(new Book("Throne of glass", 12));
        bookStore.addBook(new Book("Clean Code", 15));
    }
}
