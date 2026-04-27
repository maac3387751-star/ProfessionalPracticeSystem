package uv.lis.professionalpracticesystem.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Miguel Aguilar
 */
public class MySQLConnectionDataAccess {

    private String url;
    private String userName;
    private String passwordUser;
    private static final Logger LOGGER = Logger.getLogger(MySQLConnectionDataAccess.class.getName());

    public MySQLConnectionDataAccess() throws IllegalStateException {
        loadProperties();
    }

    private void loadProperties() throws IllegalStateException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("configdb.properties")) {
            if (input != null) {
                properties.load(input);
                this.url = properties.getProperty("database.url");
                this.userName = properties.getProperty("database.user");
                this.passwordUser = properties.getProperty("database.password");
            } else {
                LOGGER.severe("Error, unable to find config.properties");
                throw new IllegalStateException("Configuration file config.properties not found.");
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error loading database configuration properties", ex);
            throw new IllegalStateException("Error loading database configuration properties");
        }
    }

    public Connection getConnection() throws SQLException {
        Connection newConnection = DriverManager.getConnection(url, userName, passwordUser);
        LOGGER.info("Connection with Database successfully established");
        return newConnection;
    }
}
