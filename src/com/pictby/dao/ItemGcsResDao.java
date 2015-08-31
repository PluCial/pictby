package com.pictby.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pictby.meta.ItemGcsResMeta;
import com.pictby.model.Item;
import com.pictby.model.ItemGcsRes;

public class ItemGcsResDao extends DaoBase<ItemGcsRes>{
    
    /** META */
    private static final ItemGcsResMeta meta =  ItemGcsResMeta.get();
    
    /**
     * すべてのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<ItemGcsRes> getResourcesList(Item item) {
        return Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey())
                    ).asList();
    }
    
    /**
     * リソースキーリストを取得
     * @param spotId
     * @return
     */
    public List<Key> getResourcesKeyList(Item item) {
        return Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey())
                    ).asKeyList();
    }

}
