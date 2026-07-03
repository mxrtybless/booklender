package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.server.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lesson47Server extends Lesson46Server {

    public Lesson47Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/query", this::handleQueryRequest);
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
}