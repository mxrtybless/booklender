package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.model.Employee;
import kg.attractor.java.server.Cookie;
import kg.attractor.java.server.SessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private static final int SESSION_MAX_AGE = 600;

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
        visitsCookie.setMaxAge(SESSION_MAX_AGE);
        visitsCookie.setHttpOnly(true);
        setCookie(exchange, visitsCookie);

        data.put("times", times);
        data.put("globalVisit", globalVisitCounter);
        data.put("cookies", cookies);

        renderTemplate(exchange, "cookie.html", data);
    }

    protected Employee getAuthorizedEmployee(HttpExchange exchange) {
        Map<String, String> cookies = Cookie.parse(getCookies(exchange));
        String sessionId = cookies.get(SESSION_COOKIE_NAME);
        return SessionManager.findEmployeeBySessionId(sessionId);
    }

    protected void createSession(HttpExchange exchange, Employee employee) {
        String sessionId = SessionManager.createSession(employee);

        Cookie<String> sessionCookie = Cookie.make(SESSION_COOKIE_NAME, sessionId);
        sessionCookie.setMaxAge(SESSION_MAX_AGE);
        sessionCookie.setHttpOnly(true);

        setCookie(exchange, sessionCookie);
    }

    protected void removeSession(HttpExchange exchange) {
        Map<String, String> cookies = Cookie.parse(getCookies(exchange));
        String sessionId = cookies.get(SESSION_COOKIE_NAME);

        SessionManager.removeSession(sessionId);

        Cookie<String> sessionCookie = Cookie.make(SESSION_COOKIE_NAME, "deleted");
        sessionCookie.setMaxAge(0);
        sessionCookie.setHttpOnly(true);

        setCookie(exchange, sessionCookie);
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