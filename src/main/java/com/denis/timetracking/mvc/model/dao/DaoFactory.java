package com.denis.timetracking.mvc.model.dao;

import com.denis.timetracking.mvc.model.DbConnectionHandler;
import com.denis.timetracking.mvc.model.entity.EntityEnum;
import org.apache.log4j.Logger;
import java.sql.Connection;

/**
 * Created by Denis on 28.04.2018.
 * DAO factory class
 * Creates DAO by request
 */
public class DaoFactory {
    private DbConnectionHandler connectionHandler;

    private static Logger logger = Logger.getLogger(DaoFactory.class);

    public DaoFactory(){
        connectionHandler = new DbConnectionHandler("dbconfig.properties");
    }

    /**
     * Get dao abstract dao.
     *
     * @param daoName the dao name
     * @return the abstract dao
     */
    public AbstractDao getDao(String daoName){
        Connection connection = connectionHandler.getConnection();
        EntityEnum dao = EntityEnum.valueOf(daoName);
        switch (dao){
            case ACTIVITY:
                logger.info("Dao manager returns Activity Dao");
                return new ActivityDao(connection);
            case USER:
                logger.info("Dao manager returns User Dao");
                return new UserDao(connection);
            default: throw new EnumConstantNotPresentException(EntityEnum.class, dao.name());
        }
    }
}
