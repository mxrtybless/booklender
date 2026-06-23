package kg.attractor.java.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonDataLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Book> loadBooks() {
        try {
            File file = new File("src/kg/attractor/java/json/books.json");
            return Arrays.asList(mapper.readValue(file, Book[].class));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load books", e);
        }
    }

    public static List<Employee> loadEmployees() {
        try {
            File file = new File("src/kg/attractor/java/json/employees.json");
            return Arrays.asList(mapper.readValue(file, Employee[].class));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load employees", e);
        }
    }
}