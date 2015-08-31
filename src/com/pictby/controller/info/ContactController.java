package com.pictby.controller.info;

import org.slim3.controller.Navigation;

public class ContactController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        return forward("contact.jsp");
    }
}
