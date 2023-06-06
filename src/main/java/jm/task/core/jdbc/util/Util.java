package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static String url = "jdbc:mysql://localhost:3306/MySql?useSSL=false&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "root";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.DRIVER, JDBC_DRIVER);
                properties.put(Environment.URL, url);
                properties.put(Environment.USER,  user);
                properties.put(Environment.PASS,  password);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO,"create-drop");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Connection");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection createConnection() {
        Connection connection = null;

        {
            try {
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() {

    }
}