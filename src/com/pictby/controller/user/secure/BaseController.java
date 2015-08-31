package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.exception.NoLoginException;
import com.pictby.model.User;

public abstract class BaseController extends com.pictby.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        try {
            User user = getLoginUser();

            requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
            requestScope("isLocal", String.valueOf(isLocal()));
            requestScope("isLogged", String.valueOf(user != null));
            requestScope("loginUser", user);
            
            // ログインしている場合
            return execute(user);

        }catch(NoLoginException e) {
            return redirect("/user/account/login");
        }
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(User user) throws Exception;

}
