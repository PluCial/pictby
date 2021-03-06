package com.pictby.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.pictby.dao.SignupEntryDao;
import com.pictby.meta.SignupEntryMeta;
import com.pictby.model.SignupEntry;


public class SignupEntryService {
    
    /** DAO */
    private static final SignupEntryDao dao = new SignupEntryDao();
    
    /**
     * PUT
     * @param model
     * @return
     */
    public static SignupEntry put(SignupEntry model) {
        
        dao.put(model);
        
        return model;
    }
    
    /**
     * PUT
     * @param model
     * @return
     */
    public static SignupEntry put(String name, String email, String password) {
        
        SignupEntry model = new SignupEntry();
        model.setName(name);
        model.setEmail(new Email(email));
        model.setPassword(password);
        model.setKey(createKey());
        
        return put(model);
    }
    
    /**
     * キーから取得
     * <pre>存在しない場合エラー</pre>
     */
    public static SignupEntry getByKey(String keyString) throws Exception {
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
        return Datastore.createKey(SignupEntryMeta.get(), keyString);
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
