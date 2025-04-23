package utils;
import com.mysql.cj.jdbc.MysqlDataSource;

import migrations.DBMigration;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class MySQLConnection {
    public static void main(String[] args) {
        Properties prop = new Properties();
        MysqlDataSource mysqlDS = new MysqlDataSource();

        try  {
            InputStream fis = MySQLConnection.class.getClassLoader().getResourceAsStream("resources/DBPropFile.properties");
            prop.load(fis);
            mysqlDS.setURL(prop.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(prop.getProperty("USER"));
            mysqlDS.setPassword(prop.getProperty("PASSWORD"));
        } catch (Exception e) {
            System.out.println("Error loading DB config");
            e.printStackTrace();
            return;
        }

        try (Connection conn = mysqlDS.getConnection()) {
            DBMigration.migrate(conn);

        } catch (Exception e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
}
