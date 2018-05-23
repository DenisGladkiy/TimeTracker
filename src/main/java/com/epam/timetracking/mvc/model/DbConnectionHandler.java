package com.epam.timetracking.mvc.model;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Denis on 28.04.2018.
 * Connection Pool Class
 * Provides DataBase connection to DAO
 */
public class DbConnectionHandler {
    private static volatile DataSource dataSource;
    private DbPropertyManager dpm;
    private static Properties properties;

    /**
     * Instantiates a new DB connection handler.
     *
     * @param resource DB connection properties file name
     */
    public DbConnectionHandler(String resource){
        dpm = new DbPropertyManager(resource);
        properties = dpm.getProperty();
    }

    private static DataSource getDataSource(){
        if(dataSource == null){
            synchronized (DbConnectionHandler.class){
                if(dataSource == null){
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName(properties.getProperty("db.driver"));
                    ds.setUrl(properties.getProperty("db.url"));
                    ds.setUsername(properties.getProperty("db.login"));
                    ds.setPassword(properties.getProperty("db.password"));
                    ds.setMinIdle(Integer.valueOf(properties.getProperty("db.min.idle")));
                    ds.setMaxIdle(Integer.valueOf(properties.getProperty("db.max.idle")));
                    ds.setMaxOpenPreparedStatements(Integer.valueOf(properties.getProperty("db.max.open.prepare.statements")));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

    /**
     * @return the connection
     */
    public Connection getConnection(){
        try {
            Connection connection = getDataSource().getConnection();
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
