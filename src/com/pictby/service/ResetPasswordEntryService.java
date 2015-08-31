package com.pictby.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pictby.dao.ResetPasswordEntryDao;
import com.pictby.meta.ResetPasswordEntryMeta;
import com.pictby.model.ResetPasswordEntry;
import com.pictby.model.User;


public class ResetPasswordEntryService {
    
    /** DAO */
    private static final ResetPasswordEntryDao dao = new ResetPasswordEntryDao();
    
    /**
     * PUT
     * @param model
     * @return
     */
    public static ResetPasswordEntry put(ResetPasswordEntry model) {
        
        dao.put(model);
        
        return model;
    }
    
    /**
     * PUT
     * @param model
     * @return
     */
    public static ResetPasswordEntry put(User user) {
        
        ResetPasswordEntry model = new ResetPasswordEntry();
        model.setKey(createKey());
        model.getUserRef().setModel(user);
        
        return put(model);
    }
    
    /**
     * キーから取得
     * <pre>存在しない場合エラー</pre>
     */
    public static ResetPasswordEntry getByKey(String keyString) throws Exception {
        return dao.get(createKey(keyString));
    }
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(ResetPasswordEntryMeta.get(), keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    public static Key createKey() {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(uniqueKey.toString());
    }

}
