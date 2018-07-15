package com.denis.timetracking.utils;

import com.denis.timetracking.mvc.model.entity.Activity;
import com.denis.timetracking.mvc.model.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

/**
 * Created by Denis on 14.07.2018.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.setProperty(Environment.URL,
                "jdbc:mysql://localhost/time_tracking?useUnicode=yes&characterEncoding=UTF-8");
        properties.setProperty(Environment.USER, "root");
        properties.setProperty(Environment.PASS, "root1");
        properties.setProperty(Environment.SHOW_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Activity.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addProperties(properties);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static void initTestConfig(){
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.setProperty(Environment.URL,
                "jdbc:mysql://localhost/time_tracking_test?useUnicode=yes&characterEncoding=UTF-8");
        properties.setProperty(Environment.USER, "root");
        properties.setProperty(Environment.PASS, "root1");
        properties.setProperty(Environment.SHOW_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Activity.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addProperties(properties);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
