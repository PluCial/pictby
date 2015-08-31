package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pictby.enums.EntryType;
import com.pictby.model.ResetPasswordEntry;
import com.pictby.model.User;
import com.pictby.service.EMailService;
import com.pictby.service.ResetPasswordEntryService;
import com.pictby.service.UserService;
import com.pictby.validator.NoContentsValidator;

public class ResetPasswordEntryController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/user/account/resetPassword");
        }
        
        String email = asString("email");
        
        User user = UserService.getByEmail(email);
        
        if (!validate(user)) {
            return forward("/user/account/resetPassword");
        }
        
        // 不足情報を取得のため、再取得
        user = UserService.getUser(user.getUserId());
        
        // エントリーを保存
        ResetPasswordEntry entry = ResetPasswordEntryService.put(user);
        
        String entryUrl = super.getAccessDomeinUrl() + "/user/account/confirmationEmailEntry?type=" + EntryType.RESET_PASSWORD.toString() + "&entryId=" + entry.getKey().getName();
        
        // メール確認フロー
        EMailService.resetPassword(email, user.getName(), entryUrl, isLocal());
        
        return redirect("/user/account/entryCompletion?type=" + EntryType.RESET_PASSWORD.toString());
    }
    
    /**
     * バリデーション
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

        return v.validate();
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate(User user) {
        Validators v = new Validators(request);

        // メール
        v.add("email", 
            new NoContentsValidator(user, "このメールアドレスは登録されていません。"));

        return v.validate();
    }
    
    // NoContentsValidator
}
