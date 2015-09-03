package com.pictby.controller;

import org.slim3.controller.Navigation;

public class EmbedJsController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        requestScope("domein", getAccessDomeinUrl());
        
        // 他のサイトからAjax使って取得するには以下のヘッダーを追加する必要がある。
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        
        return forward("embedJs.jsp");
    }
}
