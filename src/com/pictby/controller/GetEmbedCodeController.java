package com.pictby.controller;

import org.slim3.controller.Navigation;

public class GetEmbedCodeController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        
        requestScope("domein", getAccessDomeinUrl());
        
        return forward("getEmbedCode.jsp");
    }
}
