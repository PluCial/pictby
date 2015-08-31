package com.pictby.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.pictby.dao.ItemGcsResDao;
import com.pictby.enums.ItemGcsRole;
import com.pictby.model.ItemGcsRes;
import com.pictby.model.Item;
import com.pictby.model.User;


public class GcsItemResourcesService extends GcsResourcesService {
    
    /** DAO */
    private static final ItemGcsResDao dao = new ItemGcsResDao();
    
    /** ディレクトリ名 */
    private static final String ITEM_DIRECTORY_NAME = "ITEM/";
    
    /**
     * オリジナルアイテムイメージの追加
     * @param spot
     * @param fileItem
     * @throws IOException
     */
    public static ItemGcsRes addOriginalImageResources(
            Transaction tx, 
            User user, 
            Item item,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException {
        
        Key key = createKey(user);
        
        // image objectの取得
        Image image = ImagesServiceFactory.makeImage(fileItem.getData());
        
        // 画像の切り取り
        imageCrop(image, (float)leftX / (float)image.getWidth(), (float)topY / (float)image.getHeight(), (float)rightX / (float)image.getWidth(), (float)bottomY / (float)image.getHeight());
        
        // ファイルの保存
        GcsFilename gcsFilename = saveImageToGCS(ITEM_DIRECTORY_NAME + key.getName(), fileItem.getContentType(), image);
        
        // servingUrl
        String servingUrl = getServingUrl(gcsFilename);
        
        // 保存する情報の設定
        ItemGcsRes model = new ItemGcsRes();
        model.setKey(key);
        model.setRole(ItemGcsRole.ITEM_ORIGINAL_IMAGE);
        model.setServingUrl(servingUrl);
        model.setWidth(image.getWidth());
        model.setHeight(image.getHeight());
        model.setContentType(fileItem.getContentType());
        
        model.getUserRef().setModel(user);
        model.getItemRef().setModel(item);

        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static ItemGcsRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースリストを取得
     * @param target
     * @return
     */
    private static List<ItemGcsRes> getResourcesList(Item item) {
        // TODO: キャッシュ対応
        List<ItemGcsRes> list = dao.getResourcesList(item);
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public static Map<String, ItemGcsRes> getResourcesMap(Item item) {
        
        Map<String,ItemGcsRes> map = new HashMap<String,ItemGcsRes>();
        
        List<ItemGcsRes> list = getResourcesList(item);
        if(list == null) return map;
        
        for (ItemGcsRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static ItemGcsRes getResourcesByMap(Map<String, ItemGcsRes> resourcesMap, ItemGcsRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }
    
    /**
     * アイテムリソースを全削除(用コミット)
     * @param tx
     * @param item
     */
    public static void deleteItemResourcesAll(Transaction tx, Item item) {
        List<Key> keys = dao.getResourcesKeyList(item);

        Datastore.delete(tx, keys);
    }

}
