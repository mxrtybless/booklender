package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.data.MockData;
import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;
import kg.attractor.java.server.Cookie;
import kg.attractor.java.server.SessionManager;
import kg.attractor.java.server.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {
    private static final String sessionCookieName = "sessionId";
    private static final int sessionMaxAge = 600;

    private int globalVisitCounter = 0;

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/cookies", this::cookiesHandler);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/profile", this::profileGet);
        registerGet("/logout", this::logoutGet);
        registerPost("/issue", this::issueBookPost);
        registerPost("/return", this::returnBookPost);
    }

    private void cookiesHandler(HttpExchange exchange) {
        Map<String, Object> data = new HashMap<>();
        String visitCookieName = "times";

        Map<String, String> cookies = Cookie.parse(getCookies(exchange));

        int times = getIntCookieValue(cookies, visitCookieName) + 1;
        globalVisitCounter++;

        Cookie<Integer> visitsCookie = Cookie.make(visitCookieName, times);
        visitsCookie.setMaxAge(sessionMaxAge);
        visitsCookie.setHttpOnly(true);
        setCookie(exchange, visitsCookie);

        data.put("times", times);
        data.put("globalVisit", globalVisitCounter);
        data.put("cookies", cookies);

        renderTemplate(exchange, "cookie.html", data);
    }

    private void loginGet(HttpExchange exchange) {
        Employee employee = getAuthorizedEmployee(exchange);

        if (employee != null) {
            redirect303(exchange, "/profile");
            return;
        }

        renderLoginPage(exchange, null, "");
    }

    private void loginPost(HttpExchange exchange) {
        Map<String, String> form = Utils.parseUrlEncoded(getBody(exchange), "&");

        String email = form.getOrDefault("email", "").trim().toLowerCase();
        String password = form.getOrDefault("password", "");

        Employee employee = MockData.findEmployeeByEmailAndPassword(email, password);

        if (employee == null) {
            renderLoginPage(exchange, "User not found or password is incorrect.", email);
            return;
        }

        createSession(exchange, employee);
        redirect303(exchange, "/profile");
    }

    private void profileGet(HttpExchange exchange) {
        Employee employee = getAuthorizedEmployee(exchange);

        if (employee == null) {
            Employee guest = new Employee(
                    0,
                    "unknown@example.com",
                    "Guest user",
                    "",
                    List.of(),
                    List.of()
            );

            renderProfilePage(exchange, guest, false);
            return;
        }

        renderProfilePage(exchange, employee, true);
    }

    private void logoutGet(HttpExchange exchange) {
        removeSession(exchange);
        redirect303(exchange, "/login");
    }

    private void issueBookPost(HttpExchange exchange) {
        Employee employee = getAuthorizedEmployee(exchange);

        if (employee == null) {
            redirect303(exchange, "/login");
            return;
        }

        Map<String, String> form = Utils.parseUrlEncoded(getBody(exchange), "&");
        int bookId = Utils.parseIntOrDefault(form.get("bookId"), -1);

        MockData.issueBook(bookId, employee.getId());
        redirect303(exchange, "/profile");
    }

    private void returnBookPost(HttpExchange exchange) {
        Employee employee = getAuthorizedEmployee(exchange);

        if (employee == null) {
            redirect303(exchange, "/login");
            return;
        }

        Map<String, String> form = Utils.parseUrlEncoded(getBody(exchange), "&");
        int bookId = Utils.parseIntOrDefault(form.get("bookId"), -1);

        MockData.returnBook(bookId, employee.getId());
        redirect303(exchange, "/profile");
    }

    private void renderLoginPage(HttpExchange exchange, String error, String email) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", error);
        model.put("email", email);

        renderTemplate(exchange, "login.ftl", model);
    }

    private void renderProfilePage(HttpExchange exchange, Employee employee, boolean authorized) {
        Map<String, Object> model = new HashMap<>();

        model.put("employee", employee);
        model.put("authorized", authorized);
        model.put("availableBooks", getAvailableBooks());
        model.put("canTakeBooks", authorized && employee.getCurrentBooksCount() < 2);

        renderTemplate(exchange, "profile.ftl", model);
    }

    private List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : MockData.getBooks()) {
            if (!book.isIssued()) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    protected Employee getAuthorizedEmployee(HttpExchange exchange) {
        Map<String, String> cookies = Cookie.parse(getCookies(exchange));
        String sessionId = cookies.get(sessionCookieName);
        return SessionManager.findEmployeeBySessionId(sessionId);
    }

    protected void createSession(HttpExchange exchange, Employee employee) {
        String sessionId = SessionManager.createSession(employee);

        Cookie<String> sessionCookie = Cookie.make(sessionCookieName, sessionId);
        sessionCookie.setMaxAge(sessionMaxAge);
        sessionCookie.setHttpOnly(true);

        setCookie(exchange, sessionCookie);
    }

    protected void removeSession(HttpExchange exchange) {
        Map<String, String> cookies = Cookie.parse(getCookies(exchange));
        String sessionId = cookies.get(sessionCookieName);

        SessionManager.removeSession(sessionId);

        Cookie<String> sessionCookie = Cookie.make(sessionCookieName, "deleted");
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