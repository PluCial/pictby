package com.pictby.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pictby.dao.UserDao;
import com.pictby.enums.UserTextRole;
import com.pictby.exception.ObjectNotFoundException;
import com.pictby.exception.TooManyException;
import com.pictby.meta.UserMeta;
import com.pictby.model.ItemTag;
import com.pictby.model.SocialLink;
import com.pictby.model.User;
import com.pictby.model.UserTextRes;


public class UserService {
    
    /** DAO */
    private static final UserDao dao = new UserDao();
    
    /**
     * Get
     * @return
     */
    public static User getByEmail(String email) {
        
        return dao.getByEmail(email);
    }
    
    /**
     * ログイン承認
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public static User login(String email, String password) throws Exception {
        User user = getByEmail(email);
        
        // メールもしくはパスワードが違っている場合
        if(user == null || !user.getPassword().equals(password)) {
            return null;
        }
        
        return user;
    }
    
    /**
     * ユーザーの追加
     * @param userId
     * @param email
     * @param password
     * @param lang
     * @param name
     * @param catchCopy
     * @param detail
     * @return
     * @throws NullPointerException
     * @throws TooManyException
     */
    public static User add(
            String userId,
            String email, 
            String password, 
            String name, 
            String catchCopy, 
            String detail) throws NullPointerException, TooManyException {
        
        if(StringUtil.isEmpty(userId)
                || StringUtil.isEmpty(email)
                || StringUtil.isEmpty(password)) {
            throw new NullPointerException("登録情報が不十分です");
        }
        
        // 既に存在しているかチェック
        if(dao.getByUserId(userId) != null) {
            throw new TooManyException("このUserIdは既に利用されています。");
        }
        
        // 既に存在しているかチェック
        if(getByEmail(email) != null) {
            throw new TooManyException("このメールアドレスは既に登録されています。");
        }
        
        // ユーザーモデルの設定
        User user = new User();
        user.setUserId(userId);
        user.setEmail(new Email(email));
        user.setPassword(password);
        
        Map<String,UserTextRes> textUserResourcesMap = new HashMap<String,UserTextRes>();
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            // スポットキーの作成
            Key userKey = createKey();
            user.setKey(userKey);
            
            // ユーザー情報の登録
            Datastore.put(tx, user);

            // 名前
            UserTextRes userNameResources = TextUserResourcesService.add(tx, user, UserTextRole.USER_NAME, name);
            textUserResourcesMap.put(UserTextRole.USER_NAME.toString(), userNameResources);
            
            // キャッチー
            UserTextRes userCatchResources = TextUserResourcesService.add(tx, user, UserTextRole.USER_CATCH_COPY, catchCopy);
            textUserResourcesMap.put(UserTextRole.USER_CATCH_COPY.toString(), userCatchResources);
            
            // 詳細
            UserTextRes userDetailResources = TextUserResourcesService.add(tx, user, UserTextRole.USER_DETAIL, detail);
            textUserResourcesMap.put(UserTextRole.USER_DETAIL.toString(), userDetailResources);

            tx.commit();

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
        
        // ユーザー情報を設定
        user.setTextResources(textUserResourcesMap);
        
        return user;
    }
    
    /**
     * パスワードの変更
     * @param model
     * @param password
     * @return
     */
    public static void updatePassword(User model, String password) {
        model.setPassword(password);
        
        dao.put(model);
    }
    
    /**
     * ソーシャルリンクの設定
     * @param model
     * @param socialLink
     */
    public static void settingSocial(User model, List<SocialLink> socialLinkList) {
        
        model.setSocialLinks(socialLinkList);
        
        dao.put(model);
    }
    
    /**
     * ユーザーの付属情報の設定
     * @param user
     * @param lang
     */
    private static void setResources(User user) {
        
        user.setTextResources(TextUserResourcesService.getResourcesMap(user));
        user.setGcsResources(GcsUserResourcesService.getResourcesMap(user));
        
        List<ItemTag> itemTagList = ItemTagService.getUserTagList(user);
        if(itemTagList != null) user.setItemTagList(itemTagList);
    }
    
    /**
     * ユーザー情報を取得
     * @param keyString
     * @return
     */
    public static User getUser(String userId) {
        User model = null;
        
        try {
            model = MemcacheService.getUser(userId);

        }catch(ObjectNotFoundException e) {
            // DBから取得
            model = dao.getByUserId(userId);
            if(model == null) return null;
            
            // 付属情報の追加
            setResources(model);
            
            // キャッシュを追加
            MemcacheService.addUser(model);
        }
        
        return model;
    }
    
    /**
     * 新しいユーザーリストの取得
     * @param num
     * @return
     */
    public static List<User> getNew(int num) {
        
        List<User> userList = dao.getNew(num);
        for(User user: userList) {
            setResources(user);
        }
        
        return userList;
    }
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    private static Key createKey() {
        return Datastore.allocateId(UserMeta.get());
    }

}
