package com.pictby.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pictby.meta.UserTextResMeta;
import com.pictby.model.User;
import com.pictby.model.UserTextRes;

public class UserTextResDao extends DaoBase<UserTextRes>{
    
    /** META */
    private static final UserTextResMeta meta =  UserTextResMeta.get();
    
    /**
     * ユーザーのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<UserTextRes> getResourcesList(User user) {
        return Datastore.query(meta)
                .filter(
                    meta.userRef.equal(user.getKey())
                    ).asList();
    }

}
