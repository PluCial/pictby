package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.model.User;

public class ItemAddController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        return forward("itemAdd.jsp");
    }
}
