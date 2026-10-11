package cl.demo.crud.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Conexión simple vía DriverManager para que el ejemplo corra en cualquier servidor.
 * En producción: usa un DataSource del servidor (JNDI) con pool de conexiones.
 */
public final class Db {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Db.class.getResourceAsStream("/db.properties")) {
            PROPS.load(in);
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Db() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("url"),
                PROPS.getProperty("user"),
                PROPS.getProperty("password"));
    }
}
