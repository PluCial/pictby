package com.pictby.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.pictby.dao.UserGcsResDao;
import com.pictby.enums.UserGcsRole;
import com.pictby.exception.NoContentsException;
import com.pictby.model.UserGcsRes;
import com.pictby.model.User;


public class GcsUserResourcesService extends GcsResourcesService {
    
    /** DAO */
    private static final UserGcsResDao dao = new UserGcsResDao();
    
    /** ディレクトリ名 */
    private static final String USER_DIRECTORY_NAME = "USER/";
    
    /**
     * ユーザーのアイコン画像を追加
     * @param user
     * @param fileItem
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     * @throws IOException
     */
    public static UserGcsRes addUserIconImage(
            User user, 
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
        GcsFilename gcsFilename = saveImageToGCS(USER_DIRECTORY_NAME + key.getName(), fileItem.getContentType(), image);
        
        // servingUrl
        String servingUrl = getServingUrl(gcsFilename);
        
        // 保存する情報の設定
        UserGcsRes model = new UserGcsRes();
        model.setKey(key);
        model.setRole(UserGcsRole.USER_ICON_IMAGE);
        model.setServingUrl(servingUrl);
        model.setWidth(image.getWidth());
        model.setHeight(image.getHeight());
        model.setContentType(fileItem.getContentType());

        model.getUserRef().setModel(user);
        
        // 保存
        Datastore.put(model);
        
        return model;
    }
    
    /**
     * ユーザーアイコン画像の変更(切り取り)
     * @param user
     * @param resourcesKey
     * @param fileItem
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     * @return
     * @throws IOException
     * @throws NoContentsException
     */
    public static UserGcsRes updateUserIconImage(
            User user, 
            String resourcesKey,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException, NoContentsException {
        
        if(StringUtil.isEmpty(resourcesKey)) throw new NoContentsException("更新するコンテンツはありません");
        
        UserGcsRes oldModel = getResources(resourcesKey);
        if(oldModel == null) throw new NoContentsException("更新するコンテンツはありません");
        
        // 古いモデルを無効にする
        oldModel.setInvalid(true);
        dao.put(oldModel);
        
        return addUserIconImage(user, fileItem, leftX, topY, rightX, bottomY);
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static UserGcsRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースリストを取得
     * @param target
     * @return
     */
    private static List<UserGcsRes> getResourcesList(User user) {
        // TODO: キャッシュ対応
        List<UserGcsRes> list = dao.getResourcesList(user);
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public static Map<String, UserGcsRes> getResourcesMap(User user) {
        
        Map<String,UserGcsRes> map = new HashMap<String,UserGcsRes>();
        
        List<UserGcsRes> list = getResourcesList(user);
        if(list == null) return map;
        
        for (UserGcsRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static UserGcsRes getResourcesByMap(Map<String, UserGcsRes> resourcesMap, UserGcsRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }

}
