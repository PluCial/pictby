package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;

public class LoginController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("login.jsp");
    }
}
