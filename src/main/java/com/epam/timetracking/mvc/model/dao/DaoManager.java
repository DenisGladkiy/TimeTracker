package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.mvc.model.DbConnectionHandler;
import org.apache.log4j.Logger;

import java.sql.Connection;

/**
 * Created by Denis on 28.04.2018.
 * DAO factory class
 * Creates DAO by request
 */
public class DaoManager {
    private static volatile DaoManager instance;
    private DbConnectionHandler connectionHandler;
    private enum DaoEnum{ACTIVITY, USER}
    private static Logger logger = Logger.getLogger(DaoManager.class);
    private DaoManager(){
        connectionHandler = new DbConnectionHandler("dbconfig.properties");
    }

    public static DaoManager getInstance(){
        if(instance == null){
            synchronized (DaoManager.class){
                if(instance == null){
                    instance = new DaoManager();
                }
            }
        }
        return instance;
    }

    public AbstractDao getDao(String daoName){
        Connection connection = connectionHandler.getConnection();
        DaoEnum dao = DaoEnum.valueOf(daoName);
        switch (dao){
            case ACTIVITY:
                logger.info("Dao manager returns Activity Dao");
                return new ActivityDao(connection);
            case USER:
                logger.info("Dao manager returns User Dao");
                return new UserDao(connection);
            default: throw new EnumConstantNotPresentException(DaoEnum.class, dao.name());
        }
    }
}
