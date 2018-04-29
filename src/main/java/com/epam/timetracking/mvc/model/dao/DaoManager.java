package com.epam.timetracking.mvc.model.dao;

import com.epam.timetracking.utils.DbConnectionHandler;

import java.sql.Connection;

/**
 * Created by Denis on 28.04.2018.
 */
public class DaoManager {
    private DbConnectionHandler connectionHandler;
    private enum DaoEnum{Activity, User};

    public DaoManager(){
        connectionHandler = new DbConnectionHandler();
    }

    public AbstractDao getDao(String daoName){
        Connection connection = connectionHandler.getConnection();
        DaoEnum dao = DaoEnum.valueOf(daoName);
        switch (dao){
            case Activity:
                return new ActivityDao(connection);
            case User:
                return new UserDao(connection);
            default: throw new EnumConstantNotPresentException(DaoEnum.class, dao.name());
        }
    }
}
