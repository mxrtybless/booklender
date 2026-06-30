package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kg.attractor.java.data.MockData;
import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;
import kg.attractor.java.server.BasicServer;
import kg.attractor.java.server.ContentType;
import kg.attractor.java.server.ResponseCodes;
import kg.attractor.java.server.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/sample", this::freemarkerSampleHandler);
        registerGet("/books", this::booksHandler);
        registerGet("/book", this::bookHandler);

        registerGet("/employees", this::employeesHandler);
        registerGet("/employee", this::employeeHandler);
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.html", getSampleDataModel());
    }

    private void booksHandler(HttpExchange exchange) {
        var rows = new ArrayList<Map<String, Object>>();

        for (Book book : MockData.getBooks()) {
            Map<String, Object> row = new HashMap<>();
            row.put("book", book);
            row.put("employee", MockData.findEmployeeByBook(book));
            rows.add(row);
        }

        var model = new HashMap<String, Object>();
        model.put("rows", rows);

        renderTemplate(exchange, "books.ftl", model);
    }

    private void bookHandler(HttpExchange exchange) {
        int id = getIdFromQuery(exchange);
        Book book = MockData.findBookById(id);

        if (book == null) {
            respond404(exchange);
            return;
        }

        var model = new HashMap<String, Object>();
        model.put("book", book);
        model.put("employee", MockData.findEmployeeByBook(book));

        renderTemplate(exchange, "book.ftl", model);
    }

    private void employeesHandler(HttpExchange exchange) {
        var employees = MockData.getEmployees();

        var model = new HashMap<String, Object>();
        model.put("employees", employees);

        renderTemplate(exchange, "employees.ftl", model);
    }

    private void employeeHandler(HttpExchange exchange) {
        int id = getIdFromQuery(exchange);
        Employee employee = MockData.findEmployeeById(id);

        if (employee == null) {
            respond404(exchange);
            return;
        }

        var model = new HashMap<String, Object>();
        model.put("employee", employee);

        renderTemplate(exchange, "employee.ftl", model);
    }

    protected int getIdFromQuery(HttpExchange exchange) {
        Map<String, String> query = Utils.parseUrlEncoded(exchange.getRequestURI().getRawQuery(), "&");
        return Utils.parseIntOrDefault(query.get("id"), -1);
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();

                var data = stream.toByteArray();

                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private SampleDataModel getSampleDataModel() {
        return new SampleDataModel();
    }
}