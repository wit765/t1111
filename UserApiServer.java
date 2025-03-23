import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class UserApiServer {
    public static void main(String[] args) throws Exception {
        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Create user service and handler
        UserService userService = new UserService();
        
        // Initialize database
        DatabaseConfig.initDatabase();
        
        UserHandler userHandler = new UserHandler(userService);
        
        // Create context for /api/users
        server.createContext("/api/users", userHandler);
        
        // Start the server
        server.setExecutor(null);
        server.start();
        
        System.out.println("服务器已启动，监听端口 8080");
    }
} 