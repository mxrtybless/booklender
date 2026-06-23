package kg.attractor.java.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private List<Book> currentBooks = new ArrayList<>();
    private List<Book> historyBooks = new ArrayList<>();

    public Employee(int id, String name, List<Book> currentBooks, List<Book> historyBooks) {
        this.id = id;
        this.name = name;
        this.currentBooks = currentBooks;
        this.historyBooks = historyBooks;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Book> getCurrentBooks() { return currentBooks; }
    public List<Book> getHistoryBooks() { return historyBooks; }
}