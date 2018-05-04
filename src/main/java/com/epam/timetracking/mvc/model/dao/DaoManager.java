package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.mvc.model.DbConnectionHandler;

import java.sql.Connection;

/**
 * Created by Denis on 28.04.2018.
 */
public class DaoManager {
    private static volatile DaoManager instance;
    private DbConnectionHandler connectionHandler;
    private enum DaoEnum{ACTIVITY, USER}

    private DaoManager(){
        connectionHandler = new DbConnectionHandler();
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
                return new ActivityDao(connection);
            case USER:
                return new UserDao(connection);
            default: throw new EnumConstantNotPresentException(DaoEnum.class, dao.name());
        }
    }
}
