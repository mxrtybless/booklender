package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.data.MockData;
import kg.attractor.java.model.Employee;
import kg.attractor.java.server.RouteHandler;
import kg.attractor.java.server.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lesson45Server extends Lesson44Server {

    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/register", this::registerGet);
        registerPost("/register", this::registerPost);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/profile", this::profileGet);
    }

    private void registerGet(HttpExchange exchange) {
        renderRegisterPage(exchange, null, "", "");
    }

    private void registerPost(HttpExchange exchange) {
        Map<String, String> form = Utils.parseUrlEncoded(getBody(exchange), "&");

        String email = form.getOrDefault("email", "").trim().toLowerCase();
        String name = form.getOrDefault("name", "").trim();
        String password = form.getOrDefault("password", "");

        boolean registered = MockData.addEmployee(email, name, password);

        if (registered) {
            renderRegisterPage(
                    exchange,
                    "Удачная регистрация. Теперь сотрудник может войти в систему.",
                    "",
                    ""
            );
            return;
        }

        String message = "Регистрация не удалась. Проверьте данные или используйте другой идентификатор.";

        if (MockData.findEmployeeByEmail(email) != null) {
            message = "Регистрация не удалась. Пользователь с таким идентификатором уже зарегистрирован.";
        }

        renderRegisterPage(exchange, message, email, name);
    }

    private void loginGet(HttpExchange exchange) {
        renderLoginPage(exchange, null, "");
    }

    private void loginPost(HttpExchange exchange) {
        Map<String, String> form = Utils.parseUrlEncoded(getBody(exchange), "&");

        String email = form.getOrDefault("email", "").trim().toLowerCase();
        String password = form.getOrDefault("password", "");

        Employee employee = MockData.findEmployeeByEmailAndPassword(email, password);

        if (employee == null) {
            renderLoginPage(
                    exchange,
                    "Авторизоваться не удалось, неверный идентификатор или пароль.",
                    email
            );
            return;
        }

        renderProfilePage(exchange, employee, true);
    }

    private void profileGet(HttpExchange exchange) {
        Employee employee = new Employee(
                0,
                "unknown@example.com",
                "Некий пользователь",
                "",
                new ArrayList<>(),
                new ArrayList<>()
        );

        renderProfilePage(exchange, employee, false);
    }

    private void renderRegisterPage(HttpExchange exchange, String message, String email, String name) {
        Map<String, Object> model = new HashMap<>();
        model.put("message", message);
        model.put("email", email);
        model.put("name", name);

        renderTemplate(exchange, "register.ftl", model);
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

        renderTemplate(exchange, "profile.ftl", model);
    }

    protected void registerPost(String route, RouteHandler handler) {
        getRoutes().put("POST " + route, handler);
    }
}