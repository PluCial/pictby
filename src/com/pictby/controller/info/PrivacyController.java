package com.pictby.controller.info;

import org.slim3.controller.Navigation;

public class PrivacyController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("privacy.jsp");
    }
}
