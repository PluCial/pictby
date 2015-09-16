package com.pictby.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pictby.App;
import com.pictby.dao.ItemDao;
import com.pictby.enums.ItemTextRole;
import com.pictby.exception.ObjectNotFoundException;
import com.pictby.exception.UnsuitableException;
import com.pictby.meta.ItemMeta;
import com.pictby.model.Item;
import com.pictby.model.ItemGcsRes;
import com.pictby.model.ItemTag;
import com.pictby.model.User;


public class ItemService {
    
    /** DAO */
    private static final ItemDao dao = new ItemDao();
    
    /**
     * 追加
     * @param tx
     * @param spot
     * @param price
     * @param dutyFree
     * @return
     * @throws Exception 
     */
    public static Item add(
            User user, 
            String name, 
            String detail,
            String tags,
            FileItem fileItem, int leftX, int topY, int width, int height) throws Exception {
        
        // ---------------------------------------------------
        // アイテムの設定
        // ---------------------------------------------------
        Item model = new Item();
        model.setKey(createKey(user));
        model.getUserRef().setModel(user);
        model.setUserId(user.getUserId());
        model.setSortOrder(user.getSortOrderIndex() + 1);
        
        //TODO: 臨時対応(公開ボタンの実装時に変更)
        model.setPublished(true);
        model.setPublishedDate(new Date());
        
        // ---------------------------------------------------
        // ユーザーのアイテムカウントを設定
        // ---------------------------------------------------
        user.setItemCount(user.getItemCount() + 1);
        user.setSortOrderIndex(user.getSortOrderIndex() + 1);
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            // ユーザーとアイテムの保存
            Datastore.put(tx, user, model);
            
            // アイテム名の保存
            TextItemResourcesService.add(tx, user, model, ItemTextRole.ITEM_NAME, name);
            
            // アイテム説明の保存
            TextItemResourcesService.add(tx, user, model, ItemTextRole.ITEM_DETAIL, detail);
            
            // タグの追加処理
            String[] tagsArray = {};
            if(!StringUtil.isEmpty(tags.trim())) {
                tagsArray = tags.split(",");
            }
            
            // タグの保存
            if(tagsArray.length > 0) {
                for(String tag: tagsArray) {

                    ItemTag itemTag = ItemTagService.getUserTag(user, tag.trim());
                    if(itemTag == null) {
                        // まだ登録されていない場合は新規登録
                        ItemTagService.add(tx, user, tag);

                    }else {
                        // 既に登録されている場合は、アイテムカウントを加算
                        itemTag.setItemCount(itemTag.getItemCount() + 1);
                        Datastore.put(tx, itemTag);
                    }
                }
            }
            
            // アイテム画像の保存
            ItemGcsRes imageResources = GcsItemResourcesService.addOriginalImageResources(
                tx, 
                user, 
                model, 
                fileItem, 
                leftX, 
                topY, 
                leftX + width, 
                topY + height);
            
            // テキスト検索の追加
            SearchApiService.putDocument(user, model, imageResources, name, detail, Arrays.asList(tagsArray));
            
            tx.commit();
        
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
        
        return model;
    }
    
    /**
     * タグの追加
     * @param user
     * @param item
     * @param lang
     * @param tag
     * @throws Exception
     */
    public static void itemAddTag(User user, Item item, String tag) throws Exception {
        
        if(item.getTagsList().indexOf(tag) >= 0) return;
        
        // タグを検索
        ItemTag itemTag = ItemTagService.getUserTag(user, tag.trim());

        Transaction tx = Datastore.beginTransaction();
        try {

            if(itemTag == null) {
                // まだ登録されていない場合は新規登録
                itemTag = ItemTagService.add(tx, user, tag);

            }else {
                // 既に登録されている場合は、アイテムカウントを加算
                itemTag.setItemCount(itemTag.getItemCount() + 1);
                Datastore.put(tx, itemTag);
            }

            // Documentの更新
            item.getTagsList().add(tag);
            SearchApiService.putDocument(
                user, 
                item, 
                item.getOriginalImageResources(), 
                item.getName(), 
                item.getDetail(), 
                item.getTagsList());

            // コミット
            tx.commit();

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * アイテムタグの削除
     * @param user
     * @param item
     * @param lang
     * @param tag
     * @throws Exception
     */
    public static void itemDeleteTag(User user, Item item, String tag) throws Exception {
        
        if(item.getTagsList().indexOf(tag) < 0) return;
        
        // タグを検索
        ItemTag itemTag = ItemTagService.getUserTag(user, tag.trim());
        if(itemTag == null) return;

        Transaction tx = Datastore.beginTransaction();
        try {

            // itemTagのアイテムカウントを更新
            if(itemTag.getItemCount() >= 1) {
                itemTag.setItemCount(itemTag.getItemCount() - 1);
                Datastore.put(tx, itemTag);
            }

            // itemのタグリストに含まれていた場合、Documentを更新
            if(item.getTagsList().remove(tag)) {
                SearchApiService.putDocument(
                    user, 
                    item, 
                    item.getOriginalImageResources(), 
                    item.getName(), 
                    item.getDetail(), 
                    item.getTagsList());
            }

            // コミット
            tx.commit();

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * アイテムの取得
     * @param spot
     * @param key
     * @param lang
     * @return
     * @throws UnsuitableException 
     */
    public static Item getByKey(String itemKey) throws NullPointerException {

        if(StringUtil.isEmpty(itemKey)) throw new NullPointerException();

        Item model = null;
        try {
            model = MemcacheService.getItem(itemKey);

        }catch(ObjectNotFoundException e) {
            // DBから取得
            model = dao.getOrNull(createKey(itemKey));
            if(model == null) return null;
            
            // 付属情報の追加
            setItemInfo(model);
            
            // キャッシュを追加
            MemcacheService.addItem(model);
        }
        
        return model;
    }
    
    /**
     * キーからアイテム情報を取得(付属情報なし)
     * @param itemId
     * @return
     */
    public static Item getItemModelOnly(String itemId) {
        
        if(StringUtil.isEmpty(itemId)) throw new NullPointerException();

        return dao.get(createKey(itemId));
    }
    
    /**
     * ソート順の変更
     * @param item
     * @param newOrder
     * @return
     */
    public static void changeSortOrder(Item item, double newOrder) {
        item.setSortOrder(newOrder);
        dao.put(item);
    }
    
    /**
     * ユーザーのアイテムリストを取得
     * @param user
     * @param lang
     * @return
     */
    public static S3QueryResultList<Item> getItemList(User user, String cursor) {
        
        S3QueryResultList<Item> itemList = dao.getList(user, App.USER_PORTFOLIO_ITEM_LIST_LIMIT, cursor);
        
        if(itemList == null) return null;
        
        // 詳細の追加
        for(Item item: itemList) {
            setItemInfo(item);
        }
        
        return itemList;
    }
    
    /**
     * 新着リストの取得
     * @param num
     * @param cursor
     * @return
     */
    public static S3QueryResultList<Item> getNew(int num, String cursor) {
        
        S3QueryResultList<Item> itemList = dao.getNew(num, cursor);
        
        // 詳細の追加
        for(Item item: itemList) {
            setItemInfo(item);
        }
        
        return itemList;
    }
    
    /**
     * アイテムの削除
     * @param item
     */
    public static void deleteItem(User user, Item item) {
        Transaction tx = Datastore.beginTransaction();
        try {
            // アイテムリソースの削除(すべての言語)
            TextItemResourcesService.deleteItemResourcesAll(tx, item);
            GcsItemResourcesService.deleteItemResourcesAll(tx, item);
            
            // タグのアイテム数を更新
            List<String> itemTagList = item.getTagsList();
            for(String tagName: itemTagList) {
                
                ItemTag itemTag = ItemTagService.getUserTag(user, tagName);
                
                if(itemTag.getItemCount() >= 1) {
                    itemTag.setItemCount(itemTag.getItemCount() - 1);
                    Datastore.put(tx, itemTag);
                }
            }
            
            // Userのアイテム数を更新
            if(user.getItemCount() >= 1) {
                user.setItemCount(user.getItemCount() - 1);
                Datastore.put(tx, user);
            }
            
            // アイテムの削除
            Datastore.delete(tx, item.getKey());
            
            // Search Documentの削除
            SearchApiService.deleteItemDocument(item);

            // コミット
            tx.commit();

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * アイテムの付属情報の設定
     * @param item
     * @param lang
     */
    private static void setItemInfo(Item item) {
        
        item.setTextResources(TextItemResourcesService.getResourcesMap(item));
        
        item.setGcsResources(GcsItemResourcesService.getResourcesMap(item));
        
        List<String> tagsList = SearchApiService.getItemTags(item);
        item.setTagsList(tagsList == null ? new ArrayList<String>() : tagsList);
    }
    
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(ItemMeta.get(), keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    private static Key createKey(User user) {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(user.getKey().getId() + "_" + uniqueKey.toString());
    }

}
