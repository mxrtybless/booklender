package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import kg.attractor.java.server.ContentType;
import kg.attractor.java.server.ResponseCodes;
import kg.attractor.java.server.RouteHandler;
import kg.attractor.java.server.Utils;

public class Lesson45Server extends Lesson44Server {

    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registerPageHandler);
    }
    private void registerPageHandler(HttpExchange exchange) {

        var model = new HashMap<String, Object>();

        renderTemplate(exchange, "register.ftl", model);

    }

    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void loginPost(HttpExchange exchange) {
        String cType = getContentType(exchange);
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");

        String fmt = "<p>Необработанные данные: <b>%s</b></p>"

                + "<p>Content-type: <b>%s</b></p>"

                + "<p>После обработки: <b>%s</b></p>";

        String data = String.format(fmt, raw, cType, parsed);
        redirect303(exchange, "/");

        try {

            sendByteData(exchange, ResponseCodes.OK,

                    ContentType.TEXT_HTML, data.getBytes());

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    protected void registerPost(String route, RouteHandler handler) {
        getRoutes().put("POST " + route, handler);
    }
}