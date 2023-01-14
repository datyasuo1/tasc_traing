package com.tass.orderservice.services;

import com.tass.common.model.userauthen.UserDTO;
import com.tass.common.utils.ThreadLocalCollection;

public class BaseService{

    public UserDTO getUserDTO(){
        return ThreadLocalCollection.getUserActionLog();
    }
}
