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
        connectionHandler = new DbConnectionHandler();
    }

    public static DaoManager getInstance(){
        if(instance == null){
            logger.debug("Dao Manager instance null");
            synchronized (DaoManager.class){
                if(instance == null){
                    logger.debug("Dao Manager instance null 2");
                    instance = new DaoManager();
                }
            }
        }
        logger.debug("Dao Manager return instance");
        return instance;
    }

    public AbstractDao getDao(String daoName){
        logger.debug("Dao Manager getDao");
        Connection connection = connectionHandler.getConnection();
        logger.debug("Dao Manager getDao connection = " + connection);
        DaoEnum dao = DaoEnum.valueOf(daoName);
        switch (dao){
            case ACTIVITY:
                logger.debug("Dao Manager getDao Activity");
                return new ActivityDao(connection);
            case USER:
                logger.debug("Dao Manager getDao User");
                return new UserDao(connection);
            default: throw new EnumConstantNotPresentException(DaoEnum.class, dao.name());
        }
    }
}
