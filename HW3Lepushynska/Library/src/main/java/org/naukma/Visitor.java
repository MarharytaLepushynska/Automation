package org.naukma;

/**
 * Represents a library visitor who takes books
 */
public class Visitor {
    String name;
    // TODO: List of wanted books

    public Visitor(String name) {
        this.name = name;
    }

    /**
     * Returns visitors name
     */
    public String getName() {
        return name;
    }
}

