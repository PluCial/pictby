package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.model.User;

public class LogoutController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        // セッション削除
        removeSessionScope("loginUser");

        return redirect("/");
    }
}
