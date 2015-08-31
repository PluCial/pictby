package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;

public class AddAccountController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("addAccount.jsp");
    }
}
