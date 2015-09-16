package com.pictby.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.pictby.dao.UserTextResDao;
import com.pictby.enums.UserTextRole;
import com.pictby.exception.NoContentsException;
import com.pictby.model.User;
import com.pictby.model.UserTextRes;


public class TextUserResourcesService  extends TextResourcesService{
    
    /** DAO */
    private static final UserTextResDao dao = new UserTextResDao();
    
    /**
     * 追加(用コミット)
     * @param tx
     * @param user
     * @param lang
     * @param role
     * @param content
     */
    public static UserTextRes add(
            Transaction tx, 
            User user,
            UserTextRole role, 
            String content) {
        
        UserTextRes model = new UserTextRes();
        model.setKey(createKey(user));
        model.setRole(role);
        model.setStringToContent(content);
        
        model.getUserRef().setModel(user);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * Userリソースの更新
     * @param resourcesKey
     * @param content
     * @return
     * @throws NoContentsException
     */
    public static UserTextRes update(String resourcesKey, String content) throws NoContentsException {
        UserTextRes model = getResources(resourcesKey);
        if(model == null) throw new NoContentsException("更新するコンテンツはありません");

        model.setStringToContent(content);
        dao.put(model);
        
        return model;
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static UserTextRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースリストを取得
     * @param target(User or Item)
     * @param lang
     * @return
     */
    public static List<UserTextRes> getResourcesList(User user) {
        
        List<UserTextRes> list = dao.getResourcesList(user);
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public static Map<String, UserTextRes> getResourcesMap(User user) {
        
        Map<String,UserTextRes> map = new HashMap<String,UserTextRes>();
        
        List<UserTextRes> list = getResourcesList(user);
        if(list == null) return map;
        
        for (UserTextRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static UserTextRes getResourcesByMap(Map<String, UserTextRes> resourcesMap, UserTextRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }
}
