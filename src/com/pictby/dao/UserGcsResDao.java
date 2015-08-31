package com.pictby.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pictby.meta.UserGcsResMeta;
import com.pictby.model.User;
import com.pictby.model.UserGcsRes;

public class UserGcsResDao extends DaoBase<UserGcsRes>{
    
    /** META */
    private static final UserGcsResMeta meta =  UserGcsResMeta.get();
    
    /**
     * アイテムのすべてのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<UserGcsRes> getResourcesList(User user) {
        return Datastore.query(meta)
                .filter(
                    meta.userRef.equal(user.getKey()),
                    meta.invalid.equal(false)
                    ).asList();
    }
    
    /**
     * リソースキーリストを取得
     * @param spotId
     * @return
     */
    public List<Key> getResourcesKeyList(User user) {
        return Datastore.query(meta)
                .filter(
                    meta.userRef.equal(user.getKey())
                    ).asKeyList();
    }

}
