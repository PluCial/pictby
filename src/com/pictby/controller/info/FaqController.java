package com.pictby.controller.info;

import org.slim3.controller.Navigation;

public class FaqController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("faq.jsp");
    }
}
