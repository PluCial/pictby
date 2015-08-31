package com.pictby.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pictby.meta.GcsResourcesMeta;
import com.pictby.model.GcsResources;
import com.pictby.model.User;

public class GcsResourcesDao extends DaoBase<GcsResources>{
    
    /** META */
    private static final GcsResourcesMeta meta =  GcsResourcesMeta.get();
    
    /**
     * Userのすべてのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<GcsResources> getUserAllResourcesList(User user) {
        return Datastore.query(meta)
                .filter(
                    meta.userRef.equal(user.getKey())
                    ).asList();
    }

}
