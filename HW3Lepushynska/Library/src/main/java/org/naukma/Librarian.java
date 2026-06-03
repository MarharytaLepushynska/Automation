package org.naukma;

/**
 * Represents a library worker
 */
public class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    //TODO: receiving books

    /**
     * Finds book for visitor
     * @param visitor
     * @param book
     */
    public void findBook(Visitor visitor, String book) {
        System.out.println(this.name + " is looking for " + book + " for " + visitor.getName());
    }
}
