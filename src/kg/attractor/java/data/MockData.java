package kg.attractor.java.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockData {
    private static final String EMPLOYEES_FILE = "src/kg/attractor/java/json/employees.json";

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

        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(EMPLOYEES_FILE);
            EmployeeJson[] employeeJsons = gson.fromJson(reader, EmployeeJson[].class);
            reader.close();

            if (employeeJsons == null) {
                return getDefaultEmployees();
            }

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

    public static Employee findEmployeeByEmail(String email) {
        List<Employee> employees = getEmployees();

        for (Employee employee : employees) {
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
            Book book = getBookById(bookId);

            if (book != null) {
                books.add(book);
            }
        }

        return books;
    }

    private static Book getBookById(int id) {
        List<Book> books = getBooks();

        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
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

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(EMPLOYEES_FILE);
            gson.toJson(employeeJsons, writer);
            writer.close();
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

    private static List<Employee> getDefaultEmployees() {
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