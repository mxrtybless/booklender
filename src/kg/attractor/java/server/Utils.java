package kg.attractor.java.server;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Utils {

    public static Map<String, String> parseUrlEncoded(String raw, String delimiter) {
        Map<String, String> params = new HashMap<>();

        if (raw == null || raw.isBlank()) {
            return params;
        }

        String[] pairs = raw.split(delimiter);
        Stream<Map.Entry<String, String>> stream = Arrays.stream(pairs)
                .map(Utils::decode)
                .filter(Optional::isPresent)
                .map(Optional::get);

        stream.forEach(entry -> params.put(entry.getKey(), entry.getValue()));
        return params;
    }

    public static int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static Optional<Map.Entry<String, String>> decode(String kv) {
        if (!kv.contains("=")) {
            return Optional.empty();
        }

        String[] pair = kv.split("=", 2);

        if (pair.length != 2) {
            return Optional.empty();
        }

        Charset utf8 = StandardCharsets.UTF_8;
        String key = URLDecoder.decode(pair[0], utf8);
        String value = URLDecoder.decode(pair[1], utf8);
        return Optional.of(Map.entry(key, value));
    }
}