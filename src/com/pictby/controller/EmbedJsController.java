package com.pictby.controller;

import org.slim3.controller.Navigation;

public class EmbedJsController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        requestScope("domein", getAccessDomeinUrl());
        
        return forward("embedJs.jsp");
    }
}
