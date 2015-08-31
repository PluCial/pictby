package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;

public abstract class BaseController extends com.pictby.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {


        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));
        requestScope("accessDomein", String.valueOf(getAccessDomeinUrl()));
        

        return execute();
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute() throws Exception;

}
