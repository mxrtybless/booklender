package kg.attractor.java.data;

import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();

        books.add(new Book(
                1,
                "Clean Code",
                "Robert Martin",
                "Book about writing clean and maintainable code.",
                "/images/1.jpg",
                false,
                null
        ));

        books.add(new Book(
                2,
                "Effective Java",
                "Joshua Bloch",
                "Collection of Java best practices.",
                "/images/2.jpg",
                true,
                1
        ));

        books.add(new Book(
                3,
                "Spring in Action",
                "Craig Walls",
                "Guide to Spring Framework.",
                "/images/3.jpg",
                false,
                null
        ));

        return books;
    }

    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(
                1,
                "nikita@attractor.com",
                "Danilov Nikita",
                "12345abcd",
                new ArrayList<>(),
                new ArrayList<>()
        ));
        employees.add(new Employee(
                2,
                "ivan@attractor.com",
                "Ivan Smirnov",
                "password",
                new ArrayList<>(),
                new ArrayList<>()
        ));

        return employees;
    }
}