package repository.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class used to create connections to the database
 */
public class JdbcUtils {
    private String url;
    private String user;
    private String pass;


    public JdbcUtils(Properties jdbcValues){
        this.url    = jdbcValues.getProperty("jdbc.url");   // TODO this isn't working as it should, fix
        this.user   = jdbcValues.getProperty("jdbc.user");
        this.pass   = jdbcValues.getProperty("jdbc.pass");
    }

    private Connection instance=null;

    /**
     * Gets a new connection to the database
     * @return Connection
     */
    private Connection getNewConnection() {
        System.out.println(url + " | " + user +  ' ' + pass);
        Connection con=null;
        try {
            if (user!=null && pass!=null)
                con = DriverManager.getConnection(url, user, pass);
            else
                //"jdbc:sqlite:C:\\CyAN1D3\\School 3-1\\Colectiv\\Proiect-colectiv-server\\Db1.db"
                con = DriverManager.getConnection("jdbc:sqlite:C:\\CyAN1D3\\School 3-1\\Colectiv\\Proiect-colectiv-server\\Db1.db");
        }
        catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    /**
     * Gets an instance of a database connection
     * @return Connection
     */
    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }
}
