package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {
    private int globalVisitCounter = 0;

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/cookies", this::cookiesHandler);
    }

    private void cookiesHandler(HttpExchange exchange) {
        Map<String, Object> data = new HashMap<>();
        String visitCookieName = "times";

        Map<String, String> cookies = Cookie.parse(getCookies(exchange));

        int times = getIntCookieValue(cookies, visitCookieName) + 1;
        globalVisitCounter++;

        Cookie<Integer> visitsCookie = Cookie.make(visitCookieName, times);
        visitsCookie.setMaxAge(600);
        visitsCookie.setHttpOnly(true);
        setCookie(exchange, visitsCookie);

        Cookie<String> userCookie = Cookie.make("userId", "123");
        userCookie.setMaxAge(600);
        userCookie.setHttpOnly(true);
        setCookie(exchange, userCookie);

        data.put("times", times);
        data.put("globalVisit", globalVisitCounter);
        data.put("cookies", cookies);

        renderTemplate(exchange, "cookie.html", data);
    }

    protected static String getCookies(HttpExchange exchange) {
        return exchange.getRequestHeaders()
                .getOrDefault("Cookie", List.of(""))
                .get(0);
    }

    protected void setCookie(HttpExchange exchange, Cookie<?> cookie) {
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    private int getIntCookieValue(Map<String, String> cookies, String name) {
        try {
            return Integer.parseInt(cookies.getOrDefault(name, "0"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}