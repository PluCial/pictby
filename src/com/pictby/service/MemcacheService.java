package com.pictby.service;

import org.slim3.memcache.Memcache;

import com.pictby.exception.ObjectNotFoundException;
import com.pictby.model.Item;
import com.pictby.model.User;



public class MemcacheService {
    
    /** ユーザーキー */
    private static final String USER_INFO_MEMCACHE_KEY = "user_info";
    
    /** アイテムキー */
    private static final String ITEM_INFO_MEMCACHE_KEY = "item_info";
    
    /**
     * キャッシュにユーザー情報を追加
     * @param user
     */
    public static void addUser(User model) {
        Memcache.put(USER_INFO_MEMCACHE_KEY + "_" + model.getUserId(), model);
    }
    
    /**
     * キャッシュからユーザー情報を取得
     * @param userId
     * @return
     * @throws ObjectNotFoundException 
     */
    public static User getUser(String userId) throws ObjectNotFoundException {
        
        User user = Memcache.get(USER_INFO_MEMCACHE_KEY + "_" + userId);
        if(user == null) throw new ObjectNotFoundException();
        
        return user;
    }
    
    
    /**
     * ユーザーキャッシュ削除
     * @param userId
     */
    public static void deleteUser(String userId) {
        Memcache.delete(USER_INFO_MEMCACHE_KEY + "_" + userId);
    }
    
    /**
     * キャッシュにユーザー情報を追加
     * @param user
     */
    public static void addItem(Item model) {
        Memcache.put(ITEM_INFO_MEMCACHE_KEY + "_" + model.getKey().getName(), model);
    }
    
    /**
     * キャッシュからユーザー情報を取得
     * @param userId
     * @return
     * @throws ObjectNotFoundException 
     */
    public static Item getItem(String itemKey) throws ObjectNotFoundException {
        Item item =  Memcache.get(ITEM_INFO_MEMCACHE_KEY + "_" + itemKey);
        if(item == null) throw new ObjectNotFoundException();
        
        return item;
    }
    
    
    /**
     * ユーザーキャッシュ削除
     * @param userId
     */
    public static void deleteItem(String itemKey) {
        Memcache.delete(ITEM_INFO_MEMCACHE_KEY + "_" + itemKey);
    }

}
