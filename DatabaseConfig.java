import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    // 数据库连接信息
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/employees1";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Maleipiye21";

    public static Connection getConnection() throws SQLException {
        System.out.println("正在尝试连接数据库...");
        System.out.println("数据库URL: " + DB_URL);
        System.out.println("用户名: " + DB_USER);
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("数据库连接成功！");
        return conn;
    }

    public static void initDatabase() {
        try (Connection conn = getConnection()) {
            System.out.println("正在初始化数据库...");
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employees1 (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "age INTEGER NOT NULL" +
                    ")";
            
            try (Statement stmt = conn.createStatement()) {
                System.out.println("执行SQL: " + createTableSQL);
                stmt.execute(createTableSQL);
                System.out.println("数据库初始化成功！");
            }
        } catch (SQLException e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 