package com.denis.timetracking.mvc.model.service;

import com.denis.timetracking.mvc.model.entity.EntityEnum;

/**
 * Created by Denis on 29.05.2018.
 */
public class ServiceFactory {

    public AbstractService getService(String serviceName){
        EntityEnum service = EntityEnum.valueOf(serviceName);
        switch (service){
            case ACTIVITY:
                return new ActivityService();
            case USER:
                return new UserService();
            default: throw new EnumConstantNotPresentException(EntityEnum.class, service.name());
        }
    }
}
