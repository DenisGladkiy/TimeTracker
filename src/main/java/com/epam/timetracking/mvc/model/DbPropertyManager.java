package com.epam.timetracking.mvc.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Denis on 28.04.2018.
 * Class creates DB connection properties from file
 */
public class DbPropertyManager {
    private Properties property;
    private String resource;

    public DbPropertyManager(String resource){
        this.resource = resource;
    }

    public Properties getProperty(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(resource);
        try {
            property = new Properties();
            property.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }
}
