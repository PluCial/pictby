package com.pictby.controller.info;

import org.slim3.controller.Navigation;

import com.pictby.model.User;

public abstract class BaseController extends com.pictby.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {


        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));
        requestScope("accessDomein", String.valueOf(getAccessDomeinUrl()));
        
        // -------------------------------------
        // ログインチェックと設定
        // -------------------------------------
        try {
            User loginUser = getLoginUser(); // 存在しない場合エラー
            requestScope("loginUser", loginUser);
            requestScope("isLogged", String.valueOf(true));

        }catch(Exception e) {
        }

        return execute();
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute() throws Exception;

}
