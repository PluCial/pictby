package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pictby.model.User;
import com.pictby.service.UserService;
import com.pictby.validator.LoginValidator;

public class LoginEntryController extends BaseController {

    @Override
    public Navigation execute() throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/user/account/login");
        }
        
        // クライアントの取得
        String email = asString("email");
        String password = asString("password");
        User user = UserService.login(email, password);
        
        // アカウントチェック
        if(!isClient(user)) {
            return forward("/user/account/login");
        }
        
        // 補足情報を追加
        user = UserService.getUser(user.getUserId());
        
        // ログイン処理
        sessionScope("loginUser", user);
        
        return redirect("/" + user.getUserId());
    }
    
    /**
     * バリデーション(入力)
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);

        // メール
        v.add("email", 
            v.required("メールアドレスを入力してください。"),
            v.maxlength(256, "メールアドレスが長すぎます。"), 
            v.minlength(6, "メールアドレスが短すぎます。"),
            v.regexp("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*([,;]\\s*\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", "メールアドレスが正しくありません。"));

        // パスワード
        v.add("password", 
            v.required("パスワードを入力してください。")
             );

        return v.validate();
    }
    
    /**
     * バリデーション(ログインチェック)
     * @return
     */
    private boolean isClient(User user) {
        Validators v = new Validators(request);
        
        v.add("email", new LoginValidator(user, "メールアドレスもしくはパスワードが違います。"));

        return v.validate();
    }
}
