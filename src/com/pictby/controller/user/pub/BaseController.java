package com.pictby.controller.user.pub;


import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.exception.NoContentsException;
import com.pictby.model.User;
import com.pictby.service.UserService;

public abstract class BaseController extends com.pictby.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        // -------------------------------------
        // ユーザー情報を取得
        // -------------------------------------
        User user = getUser();
        if(user == null) throw new NoContentsException();
        
        if(user.isInvalid()) throw new NoContentsException();
        
        requestScope("user", user);
        
        // -------------------------------------
        // リクエストスコープの基本設定
        // -------------------------------------
        setPageTitle(user.getName());
        setPageDescription(user.getCatchCopy());
        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));
        
        // -------------------------------------
        // ログインチェックと設定
        // -------------------------------------
        try {
            User loginUser = getLoginUser(); // 存在しない場合エラー
            requestScope("loginUser", loginUser);
            requestScope("isLogged", String.valueOf(true));
            
            // オーナーチェック
            boolean isOwner = user.getUserId().equals(loginUser.getUserId());
            requestScope("isOwner", String.valueOf(isOwner));

            return execute(user, true, isOwner);

        }catch(Exception e) {
            return execute(user, false, false);
        }
    }
    
    /**
     * 対象スポットの取得
     * 
     * @return
     * @throws NoContentsException
     */
    public User getUser() throws NoContentsException {

        String userId = asString("userId");
        
        // spot Id がNullの場合
        if(StringUtil.isEmpty(userId)) {
            throw new NoContentsException();
        }
        
        // スポットの取得
        User user = UserService.getUser(userId);

        if(user == null) throw new NoContentsException();

        return user;
    }

    /**
     * リクエスト処理
     * @param spot
     * @param client
     * @param isClientLogged
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(User user, boolean isLogged, boolean isOwner) throws Exception;

}
