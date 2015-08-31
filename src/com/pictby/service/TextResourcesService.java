package com.pictby.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pictby.meta.TextResourcesMeta;
import com.pictby.model.User;


public class TextResourcesService {

    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(TextResourcesMeta.get(), keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    public static Key createKey(User user) {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(user.getKey().getId() + "_" + uniqueKey.toString());
    }

}
