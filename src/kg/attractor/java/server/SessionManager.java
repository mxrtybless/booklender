package kg.attractor.java.server;

import kg.attractor.java.data.MockData;
import kg.attractor.java.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, Integer> sessions = new HashMap<>();

    public static String createSession(Employee employee) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, employee.getId());
        return sessionId;
    }

    public static Employee findEmployeeBySessionId(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return null;
        }

        Integer employeeId = sessions.get(sessionId);

        if (employeeId == null) {
            return null;
        }

        return MockData.findEmployeeById(employeeId);
    }

    public static void removeSession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return;
        }

        sessions.remove(sessionId);
    }
}