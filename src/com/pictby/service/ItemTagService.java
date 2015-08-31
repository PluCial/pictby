package com.pictby.service;

import java.util.List;
import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pictby.dao.ItemTagDao;
import com.pictby.meta.ItemTagMeta;
import com.pictby.model.ItemTag;
import com.pictby.model.User;


public class ItemTagService {
    
    /** DAO */
    private static final ItemTagDao dao = new ItemTagDao();
    
    /**
     * 追加
     * @param tx
     * @param user
     * @return
     */
    public static ItemTag add(Transaction tx, User user, String tagName) {
        ItemTag model = new ItemTag();
        model.setKey(createKey(user));
        model.setTagName(tagName);
        model.setItemCount(model.getItemCount() + 1);
        model.getUserRef().setModel(user);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * Userタグリスト(Modelのみ)の取得
     * @param user
     * @param lang
     * @return
     */
    public static List<ItemTag> getUserTagList(User user) {
        return dao.getUserTagList(user);
    }
    
    /**
     * タグの取得
     * @param user
     * @param lang
     * @param tagName
     * @return
     */
    public static ItemTag getUserTag(User user, String tagName) {
        List<ItemTag> itemTagList = getUserTagList(user);
        
        for(ItemTag itemTag: itemTagList) {
            if(itemTag.getTagName().equals(tagName)) return itemTag;
        }
        
        return null;
    }
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(ItemTagMeta.get(), keyString);
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
