import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserHandler implements HttpHandler {
    private final UserService userService;
    private final Gson gson;

    public UserHandler(UserService userService) {
        this.userService = userService;
        this.gson = new Gson();
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, Object response) throws IOException {
        String jsonResponse = gson.toJson(response);
        System.out.println("Sending response: " + jsonResponse); // Debug log
        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        System.out.println("Received request: " + method + " " + path); // Debug log

        try {
            if (path.startsWith("/api/users")) {
                if (method.equals("GET")) {
                    if (path.equals("/api/users")) {
                        // Get all users
                        List<User> users = userService.getAllUsers();
                        System.out.println("Found " + users.size() + " users"); // Debug log
                        sendJsonResponse(exchange, 200, users);
                    } else {
                        // Get user by ID
                        String idStr = path.substring("/api/users/".length());
                        try {
                            int id = Integer.parseInt(idStr);
                            User user = userService.getUser(id);
                            if (user != null) {
                                sendJsonResponse(exchange, 200, user);
                            } else {
                                Map<String, String> error = new HashMap<>();
                                error.put("error", "User not found");
                                sendJsonResponse(exchange, 404, error);
                            }
                        } catch (NumberFormatException e) {
                            Map<String, String> error = new HashMap<>();
                            error.put("error", "Invalid user ID format");
                            sendJsonResponse(exchange, 400, error);
                        }
                    }
                } else if (method.equals("POST")) {
                    // Create new user
                    InputStream is = exchange.getRequestBody();
                    String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("Received POST body: " + body); // Debug log
                    JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
                    String name = jsonObject.get("name").getAsString();
                    int age = jsonObject.get("age").getAsInt();
                    
                    User newUser = userService.createUser(name, age);
                    sendJsonResponse(exchange, 201, newUser);
                } else if (method.equals("PUT")) {
                    // Update user
                    String idStr = path.substring("/api/users/".length());
                    try {
                        int id = Integer.parseInt(idStr);
                        InputStream is = exchange.getRequestBody();
                        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
                        String name = jsonObject.get("name").getAsString();
                        int age = jsonObject.get("age").getAsInt();
                        
                        User updatedUser = userService.updateUser(id, name, age);
                        if (updatedUser != null) {
                            sendJsonResponse(exchange, 200, updatedUser);
                        } else {
                            Map<String, String> error = new HashMap<>();
                            error.put("error", "User not found");
                            sendJsonResponse(exchange, 404, error);
                        }
                    } catch (NumberFormatException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("error", "Invalid user ID format");
                        sendJsonResponse(exchange, 400, error);
                    }
                } else if (method.equals("DELETE")) {
                    // Delete user
                    String idStr = path.substring("/api/users/".length());
                    try {
                        int id = Integer.parseInt(idStr);
                        boolean deleted = userService.deleteUser(id);
                        if (deleted) {
                            Map<String, String> response = new HashMap<>();
                            response.put("message", "User deleted successfully");
                            sendJsonResponse(exchange, 200, response);
                        } else {
                            Map<String, String> error = new HashMap<>();
                            error.put("error", "User not found");
                            sendJsonResponse(exchange, 404, error);
                        }
                    } catch (NumberFormatException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("error", "Invalid user ID format");
                        sendJsonResponse(exchange, 400, error);
                    }
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Not Found");
                sendJsonResponse(exchange, 404, error);
            }
        } catch (Exception e) {
            System.out.println("Error processing request: " + e.getMessage()); // Debug log
            e.printStackTrace(); // Debug log
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal Server Error: " + e.getMessage());
            sendJsonResponse(exchange, 500, error);
        }
    }
} 