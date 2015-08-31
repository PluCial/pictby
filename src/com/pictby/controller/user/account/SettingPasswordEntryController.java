package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pictby.exception.NoContentsException;
import com.pictby.model.ResetPasswordEntry;
import com.pictby.model.User;
import com.pictby.service.ResetPasswordEntryService;
import com.pictby.service.UserService;
import com.pictby.validator.PasswordConfirmationValidator;

public class SettingPasswordEntryController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/user/account/settingPassword");
        }
        
        String password = asString("password");
        String entryId = asString("entryId");
        
        // パスワード確認入力チェック
        if (!validate(password)) {
            return forward("/user/account/settingPassword");
        }
        
        if(StringUtil.isEmpty(entryId)) {
            throw new NoContentsException();
        }
        
        // エントリーを保存
        ResetPasswordEntry entry = ResetPasswordEntryService.getByKey(entryId);
        if(entry == null) {
            throw new NoContentsException();
        }
        
        // Userの取得
        User user = entry.getUserRef().getModel();
        
        // パスワードの変更
        UserService.updatePassword(user, password);
        
        // 補足情報を追加のため、再取得
        user = UserService.getUser(user.getUserId());
        
        // ログイン処理
        sessionScope("loginUser", user);
        
        return redirect("/" + user.getUserId());
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // パスワード
        v.add("password", 
            v.required("パスワードを入力してください。"),
            v.minlength(5, "パスワードは5文字以上必要です。"),
            v.regexp("^[a-zA-Z0-9]+$", "パスワードに使用できるのは半角英数字のみです。")
             );
        
        // 確認用パスワード
        v.add("passwordConfirmation",
            v.required("確認用のパスワードを入力してください。")
             );

        return v.validate();
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate(String password) {
        Validators v = new Validators(request);

        // パスワード と 確認用パスワードの相関チェック
        v.add("passwordConfirmation",
            new PasswordConfirmationValidator(password, "入力したパスワードが異なります。<br />もう一度確認用パスワードをご入力ください。")
             );

        return v.validate();
    }
}
