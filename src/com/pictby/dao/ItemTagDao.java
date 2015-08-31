package com.pictby.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.pictby.meta.ItemTagMeta;
import com.pictby.model.ItemTag;
import com.pictby.model.User;

public class ItemTagDao extends DaoBase<ItemTag>{
    
    /** META */
    private static final ItemTagMeta meta =  ItemTagMeta.get();
    
    /**
     * ユーザーのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<ItemTag> getUserTagList(User user) {
        return Datastore.query(meta)
                .filter(
                    meta.userRef.equal(user.getKey())
                    )
                .sort(
                    new Sort(meta.itemCount, SortDirection.DESCENDING))
                .asList();
    }

}
