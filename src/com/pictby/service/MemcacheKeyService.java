package com.pictby.service;



public class MemcacheKeyService {
    
    /** ユーザーキー */
    private static final String USER_INFO_MEMCACHE_KEY = "user_info";
    
    /** アイテムキー */
    private static final String ITEM_INFO_MEMCACHE_KEY = "item_info";
    
    // ------------------------------------------------------
    // スポット
    // ------------------------------------------------------
    
    /**
     * ユーザーキーの取得
     * @param spotId
     * @param lang
     * @return
     */
    public static String getUserKey(String userId) {
        return USER_INFO_MEMCACHE_KEY + "_" + userId;
    }
    
    /**
     * アイテムキーの取得
     * @param spotId
     * @param lang
     * @return
     */
    public static String getItemKey(String itemId) {
        return ITEM_INFO_MEMCACHE_KEY + "_" + itemId;
    }

}
