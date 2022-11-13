package repository;

import domain.User;
import org.springframework.stereotype.Component;
import repository.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Database containing users
 */
@Component
public class UserRepositoryDatabase implements UserRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public UserRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public void test() {
        System.out.println("Consider yourself tested");
    }

    @Override
    public User add(User user) {

        Connection con= dbUtils.getConnection();
            try(PreparedStatement preSmt=con.prepareStatement("insert into Users (name,email,password) values (?,?,?)")){

                preSmt.setString(1,user.getName());
                preSmt.setString(2,user.getEmail());
                preSmt.setString(3,user.getPassword());
                int result=preSmt.executeUpdate();

                try (ResultSet generatedKeys = preSmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                return user;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;

    }

    @Override
    public User delete(User e) {
        return null;
    }

    @Override
    public User find(User e) {
        return null;
    }

    @Override
    public User update(User e) {
        return null;
    }

    @Override
    public List<User> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Users");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");

                User user = new User(id, name, email, password);
                users.add(user);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all users!");
        }
        return users;
    }

    @Override
    public int size() {
        return -1;
    }
}
