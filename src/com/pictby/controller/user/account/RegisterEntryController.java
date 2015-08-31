package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pictby.enums.EntryType;
import com.pictby.model.SignupEntry;
import com.pictby.model.User;
import com.pictby.service.EMailService;
import com.pictby.service.SignupEntryService;
import com.pictby.service.UserService;
import com.pictby.validator.PasswordConfirmationValidator;
import com.pictby.validator.TooManyValidator;

public class RegisterEntryController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/user/account/register");
        }
        
        String name = asString("name");
        String email = asString("email");
        String password = asString("password");
        
        // メール重複チェック & パスワード確認入力チェック
        if (!validate(email, password)) {
            return forward("/user/account/register");
        }
        
        // エントリーを保存
        SignupEntry entry = SignupEntryService.put(name, email, password);
        
        String registerUrl = super.getAccessDomeinUrl() + "/user/account/confirmationEmailEntry?type=" + EntryType.REGISTER.toString() + "&entryId=" + entry.getKey().getName();
        
        // メール確認フロー
        EMailService.register(email, name, registerUrl, isLocal());
        
        return redirect("/user/account/entryCompletion?type=" + EntryType.REGISTER.toString());
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // 名前
        v.add("name", 
            v.required("名前を入力してください。")
             );

        // メール
        v.add("email", 
            v.required("メールアドレスを入力してください。"),
            v.maxlength(256, "メールアドレスが長すぎます。"), 
            v.minlength(6, "メールアドレスが短すぎます。"),
            v.regexp("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*([,;]\\s*\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", "メールアドレスが正しくありません。"));

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
        
        // 規約確認
        v.add("agreeTerms", 
            v.required("登録するには規約やプライバシーポリシーに同意して頂く必要があります。")
             );

        return v.validate();
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate(String email, String password) {
        Validators v = new Validators(request);
        
        User user = UserService.getByEmail(email);

        // パスワード と 確認用パスワードの相関チェック
        v.add("passwordConfirmation",
            new PasswordConfirmationValidator(password, "入力したパスワードが異なります。<br />もう一度確認用パスワードをご入力ください。")
             );
        
        // メール
        v.add("email", 
            new TooManyValidator(user, "このメールアドレスは既に利用されています。<br />ログインをする場合はログイン画面をご利用ください。"));

        return v.validate();
    }
}
