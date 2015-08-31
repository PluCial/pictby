package com.pictby.controller.info;

import org.slim3.controller.Navigation;

public class AboutController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("about.jsp");
    }
}
