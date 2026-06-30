package kg.attractor.java.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockData {
    private static final String booksFile = "src/kg/attractor/java/json/books.json";
    private static final String employeesFile = "src/kg/attractor/java/json/employees.json";

    public static List<Book> getBooks() {
        try (FileReader reader = new FileReader(booksFile)) {
            Gson gson = new Gson();
            Book[] books = gson.fromJson(reader, Book[].class);

            if (books == null) {
                return getDefaultBooks();
            }

            return new ArrayList<>(Arrays.asList(books));
        } catch (Exception e) {
            return getDefaultBooks();
        }
    }

    public static List<Employee> getEmployees() {
        try (FileReader reader = new FileReader(employeesFile)) {
            Gson gson = new Gson();
            EmployeeJson[] employeeJsons = gson.fromJson(reader, EmployeeJson[].class);

            if (employeeJsons == null) {
                return getDefaultEmployees();
            }

            List<Employee> employees = new ArrayList<>();

            for (EmployeeJson employeeJson : employeeJsons) {
                employees.add(new Employee(
                        employeeJson.id,
                        employeeJson.email,
                        employeeJson.name,
                        employeeJson.password,
                        getBooksByIds(employeeJson.currentBooks),
                        getBooksByIds(employeeJson.historyBooks)
                ));
            }

            return employees;
        } catch (Exception e) {
            return getDefaultEmployees();
        }
    }

    public static Book findBookById(int id) {
        for (Book book : getBooks()) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    public static Employee findEmployeeById(int id) {
        for (Employee employee : getEmployees()) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        return null;
    }

    public static Employee findEmployeeByEmail(String email) {
        if (email == null) {
            return null;
        }

        for (Employee employee : getEmployees()) {
            if (employee.getEmail().equalsIgnoreCase(email)) {
                return employee;
            }
        }

        return null;
    }

    public static Employee findEmployeeByEmailAndPassword(String email, String password) {
        Employee employee = findEmployeeByEmail(email);

        if (employee == null) {
            return null;
        }

        if (!employee.getPassword().equals(password)) {
            return null;
        }

        return employee;
    }

    public static Employee findEmployeeByBook(Book book) {
        if (book == null || book.getIssuedTo() == null) {
            return null;
        }

        return findEmployeeById(book.getIssuedTo());
    }

    public static boolean addEmployee(String email, String name, String password) {
        if (email == null || email.isBlank()) {
            return false;
        }

        if (name == null || name.isBlank()) {
            return false;
        }

        if (password == null || password.isBlank()) {
            return false;
        }

        if (findEmployeeByEmail(email) != null) {
            return false;
        }

        List<Employee> employees = getEmployees();

        Employee employee = new Employee(
                getNextEmployeeId(employees),
                email.trim().toLowerCase(),
                name.trim(),
                password,
                new ArrayList<>(),
                new ArrayList<>()
        );

        employees.add(employee);
        saveEmployees(employees);
        return true;
    }

    public static boolean issueBook(int bookId, int employeeId) {
        List<Book> books = getBooks();
        List<Employee> employees = getEmployees();

        Book book = findBookById(books, bookId);
        Employee employee = findEmployeeById(employees, employeeId);

        if (book == null || employee == null) {
            return false;
        }

        if (book.isIssued()) {
            return false;
        }

        if (employee.getCurrentBooksCount() >= 2) {
            return false;
        }

        book.setIssued(true);
        book.setIssuedTo(employee.getId());

        if (!employeeHasBook(employee.getCurrentBooks(), book.getId())) {
            employee.getCurrentBooks().add(book);
        }

        saveBooks(books);
        saveEmployees(employees);
        return true;
    }

    public static boolean returnBook(int bookId, int employeeId) {
        List<Book> books = getBooks();
        List<Employee> employees = getEmployees();

        Book book = findBookById(books, bookId);
        Employee employee = findEmployeeById(employees, employeeId);

        if (book == null || employee == null) {
            return false;
        }

        if (!book.isIssued()) {
            return false;
        }

        if (book.getIssuedTo() == null || book.getIssuedTo() != employee.getId()) {
            return false;
        }

        book.setIssued(false);
        book.setIssuedTo(null);

        removeBookFromList(employee.getCurrentBooks(), book.getId());

        if (!employeeHasBook(employee.getHistoryBooks(), book.getId())) {
            employee.getHistoryBooks().add(book);
        }

        saveBooks(books);
        saveEmployees(employees);
        return true;
    }

    private static Book findBookById(List<Book> books, int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    private static Employee findEmployeeById(List<Employee> employees, int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        return null;
    }

    private static boolean employeeHasBook(List<Book> books, int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return true;
            }
        }

        return false;
    }

    private static void removeBookFromList(List<Book> books, int bookId) {
        books.removeIf(book -> book.getId() == bookId);
    }

    private static int getNextEmployeeId(List<Employee> employees) {
        int maxId = 0;

        for (Employee employee : employees) {
            if (employee.getId() > maxId) {
                maxId = employee.getId();
            }
        }

        return maxId + 1;
    }

    private static List<Book> getBooksByIds(List<Integer> bookIds) {
        List<Book> books = new ArrayList<>();

        if (bookIds == null) {
            return books;
        }

        for (Integer bookId : bookIds) {
            if (bookId == null) {
                continue;
            }

            Book book = findBookById(bookId);

            if (book != null) {
                books.add(book);
            }
        }

        return books;
    }

    private static void saveBooks(List<Book> books) {
        try (FileWriter writer = new FileWriter(booksFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveEmployees(List<Employee> employees) {
        List<EmployeeJson> employeeJsons = new ArrayList<>();

        for (Employee employee : employees) {
            employeeJsons.add(new EmployeeJson(
                    employee.getId(),
                    employee.getEmail(),
                    employee.getName(),
                    employee.getPassword(),
                    getBookIds(employee.getCurrentBooks()),
                    getBookIds(employee.getHistoryBooks())
            ));
        }

        try (FileWriter writer = new FileWriter(employeesFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(employeeJsons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getBookIds(List<Book> books) {
        List<Integer> bookIds = new ArrayList<>();

        if (books == null) {
            return bookIds;
        }

        for (Book book : books) {
            bookIds.add(book.getId());
        }

        return bookIds;
    }

    private static List<Book> getDefaultBooks() {
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

    private static List<Employee> getDefaultEmployees() {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(
                1,
                "nikita@attractor.com",
                "Danilov Nikita",
                "12345abcd",
                getBooksByIds(List.of(2)),
                getBooksByIds(List.of(1))
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

    private static class EmployeeJson {
        private int id;
        private String email;
        private String name;
        private String password;
        private List<Integer> currentBooks;
        private List<Integer> historyBooks;

        public EmployeeJson(int id,
                            String email,
                            String name,
                            String password,
                            List<Integer> currentBooks,
                            List<Integer> historyBooks) {

            this.id = id;
            this.email = email;
            this.name = name;
            this.password = password;
            this.currentBooks = currentBooks;
            this.historyBooks = historyBooks;
        }
    }
}