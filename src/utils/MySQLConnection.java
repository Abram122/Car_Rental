// just to remeber i think we need to adjust our database schema to make it stuff like VARCHAR(100) to accommodate the longer hash strings.
// because bCrypt hashes are ~60 characters
package utils;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection connection;
    private MysqlDataSource dataSource;

    private MySQLConnection() {
        try {
            Properties prop = new Properties();
            InputStream fis = getClass().getClassLoader().getResourceAsStream("resources/DBPropFile.properties");
            prop.load(fis);

            dataSource = new MysqlDataSource();
            dataSource.setURL(prop.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(prop.getProperty("USER"));
            dataSource.setPassword(prop.getProperty("PASSWORD"));

            connection = dataSource.getConnection();
            System.out.println("DB connection initialized successfully");
        } catch (Exception e) {
            System.out.println("Error initializing DB connection");
            e.printStackTrace();
        }
    }

    public static MySQLConnection getInstance() {
        if (instance == null) {
            synchronized (MySQLConnection.class) {
                if (instance == null) {
                    instance = new MySQLConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error getting DB connection");
            e.printStackTrace();
        }
        return connection;
    }
}
