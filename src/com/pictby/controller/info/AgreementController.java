package com.pictby.controller.info;

import org.slim3.controller.Navigation;

public class AgreementController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("agreement.jsp");
    }
}
