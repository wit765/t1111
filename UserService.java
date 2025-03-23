import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public User createUser(String name, int age) {
        String sql = "INSERT INTO employees1 (name, age) VALUES (?, ?) RETURNING id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new User(id, name, age);
                }
            }
        } catch (SQLException e) {
            System.err.println("创建用户失败: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(int id) {
        String sql = "SELECT * FROM employees1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("获取用户失败: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM employees1";
        try (Connection conn = DatabaseConfig.getConnection()) {
            System.out.println("正在连接数据库...");
            System.out.println("执行查询: " + sql);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                System.out.println("查询执行成功，开始获取结果...");
                while (rs.next()) {
                    User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                    );
                    users.add(user);
                    System.out.println("找到用户: " + user);
                }
                System.out.println("总共找到 " + users.size() + " 个用户");
            }
        } catch (SQLException e) {
            System.err.println("获取所有用户失败: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public User updateUser(int id, String name, int age) {
        String sql = "UPDATE employees1 SET name = ?, age = ? WHERE id = ? RETURNING *";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("更新用户失败: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM employees1 WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("删除用户失败: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
} 