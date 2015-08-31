package com.pictby.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pictby.meta.TextTagResourcesMeta;
import com.pictby.model.TextTagResources;
import com.pictby.model.User;

public class TextTagResourcesDao extends DaoBase<TextTagResources>{

    /** META */
    private static final  TextTagResourcesMeta meta =  TextTagResourcesMeta.get();
    
    /**
     * ユーザーのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<TextTagResources> getUserResourcesList(User user) {
        return Datastore.query(meta)
                .filter(
                    meta.userRef.equal(user.getKey())
                    )
                .asList();
    }
}
