package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class Util {
    public static final String URL = "jdbc:postgresql://localhost:5432/task1";
    public static final String USER = "postgres";
    public static final String PASSWORD = "rootroot";
    public static final String driver_class = "org.postgresql.Driver";

    public static SessionFactory sessionFactory;

    public static SessionFactory sessionFactory() throws SQLException {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", driver_class);
            configuration.setProperty("hibernate.connection.url", URL);
            configuration.setProperty("hibernate.connection.username", USER);
            configuration.setProperty("hibernate.connection.password", PASSWORD);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
}
