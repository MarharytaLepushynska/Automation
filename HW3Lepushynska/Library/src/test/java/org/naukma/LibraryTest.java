package org.naukma;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LibraryTest {

    @Test
    void booksShouldAddFromFile(){
        File books = new File("bookList.txt");
        Library library = new Library();
        library.addBooks(books);

        assertFalse(library.getBooks().isEmpty());
    }
}
