package kg.attractor.java.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String email;
    private String name;
    private String password;
    private List<Book> currentBooks = new ArrayList<>();
    private List<Book> historyBooks = new ArrayList<>();

    public Employee() {
    }

    public Employee(int id,
                    String email,
                    String name,
                    String password,
                    List<Book> currentBooks,
                    List<Book> historyBooks) {

        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.currentBooks = currentBooks == null ? new ArrayList<>() : currentBooks;
        this.historyBooks = historyBooks == null ? new ArrayList<>() : historyBooks;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getCurrentBooks() {
        return currentBooks;
    }

    public List<Book> getHistoryBooks() {
        return historyBooks;
    }

    public int getCurrentBooksCount() {
        return currentBooks.size();
    }

    public int getHistoryBooksCount() {
        return historyBooks.size();
    }
}