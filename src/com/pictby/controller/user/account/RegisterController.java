package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;

public class RegisterController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("register.jsp");
    }
}
