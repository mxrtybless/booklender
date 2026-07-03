package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.data.MockData;
import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;
import kg.attractor.java.server.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lesson47Server extends Lesson46Server {

    public Lesson47Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/query", this::handleQueryRequest);
        registerGet("/book", this::bookHandler);
    }

    protected String getQueryParams(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        return Objects.nonNull(query) ? query : "";
    }

    private void handleQueryRequest(HttpExchange exchange) {
        String queryParams = getQueryParams(exchange);

        Map<String, String> params = Utils.parseUrlEncoded(queryParams, "&");

        Map<String, Object> data = new HashMap<>();
        data.put("params", params);

        renderTemplate(exchange, "query.ftl", data);
    }

    private void bookHandler(HttpExchange exchange) {
        Map<String, String> query = Utils.parseUrlEncoded(exchange.getRequestURI().getRawQuery(), "&");
        int id = Utils.parseIntOrDefault(query.get("id"), -1);

        Book book = MockData.findBookById(id);

        if (book == null) {
            respond404(exchange);
            return;
        }

        Employee employee = MockData.findEmployeeByBook(book);
        Employee authorizedEmployee = getAuthorizedEmployee(exchange);

        boolean canTakeBook = authorizedEmployee != null
                && !book.isIssued()
                && authorizedEmployee.getCurrentBooksCount() < 2;

        boolean canReturnBook = authorizedEmployee != null
                && book.getIssuedTo() != null
                && book.getIssuedTo() == authorizedEmployee.getId();

        Map<String, Object> model = new HashMap<>();
        model.put("book", book);
        model.put("employee", employee);
        model.put("authorizedEmployee", authorizedEmployee);
        model.put("canTakeBook", canTakeBook);
        model.put("canReturnBook", canReturnBook);

        renderTemplate(exchange, "book.ftl", model);
    }
}