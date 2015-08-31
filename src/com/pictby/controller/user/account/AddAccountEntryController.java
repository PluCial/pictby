package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pictby.model.SignupEntry;
import com.pictby.model.User;
import com.pictby.service.SignupEntryService;
import com.pictby.service.UserService;
import com.pictby.validator.TooManyValidator;

public class AddAccountEntryController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/user/account/addAccount.jsp");
        }
        
        String entryId = asString("entryId");
        
        // entryIdチェック
        if(StringUtil.isEmpty(entryId)) {
            return redirect("/user/account/register");
        }
        
        // DBからエントリー情報を取得
        SignupEntry signupEntry = SignupEntryService.getByKey(entryId);
        
        // SignupEntryの存在チェック
        if(signupEntry == null) {
            return redirect("/user/account/register");
        }
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String userId = asString("userId");
        String catchCopy = asString("catchCopy");
        String detail = asString("detail");
        String langString = asString("lang");
        
        // TODO: 国際化時に対応
        if(StringUtil.isEmpty(langString)) {
            langString = "ja";
        }
        
        // ------------------------------------------
        // userId重複チェック
        // ------------------------------------------
        User user = UserService.getUser(userId);
        if (!validate(user)) {
            return forward("/user/account/addAccount.jsp");
        }
        
        // ユーザー登録
        user = UserService.add(
            userId, 
            signupEntry.getEmail().getEmail(), 
            signupEntry.getPassword(), 
            signupEntry.getName(), 
            catchCopy, 
            detail);
        
        // ------------------------------------------
        // ログインセッションの設定
        // ------------------------------------------
        sessionScope("loginUser", user);
        
        return redirect("/" + user.getUserId());
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // userId
        v.add("userId", 
            v.required("ユーザーIDを入力してください。"), 
            v.minlength(6, "IDは6文字以上必要です。"),
            v.regexp("^[a-z0-9_-]+$", "スポットIDで使用できる文字は半角英語(小文字)、0〜9の数字、[-]、[_]のみです。"));
        
        // キャッチフレーズ
        v.add("catchCopy", 
            v.required("キャッチフレーズを入力してください。"),
            v.maxlength(50, "キャッチフレーズは最大50文字です。")
                );
        
        // コンテンツ
        v.add("detail", 
            v.required("自己紹介を入力してください。")
        );
        
        return v.validate();
    }
    
    /**
     * ID重複バリデーション
     * @return
     */
   private boolean validate(User user) {
       Validators v = new Validators(request);

       v.add("userId", v.required(), new TooManyValidator(user, "このユーザーIDは既に利用されています。"));
       
       return v.validate();
   }
}
