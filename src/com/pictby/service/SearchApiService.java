package com.pictby.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortExpression.SortDirection;
import com.google.appengine.api.search.SortOptions;
import com.pictby.App;
import com.pictby.model.Item;
import com.pictby.model.ItemGcsRes;
import com.pictby.model.User;

public class SearchApiService {
    
    private static final String ITEM_DOCUMENT_INDEX = "item_document_index";

    /**
     * インデックスの取得
     * @param userModel
     * @return
     */
    private static Index getDocumentIndex() {
        return SearchServiceFactory.getSearchService()
                .getIndex(IndexSpec.newBuilder()
                    .setName(ITEM_DOCUMENT_INDEX));
    }
    
    /**
     * ドキュメントのキーを取得
     * @param item
     * @param lang
     * @return
     */
    private static String getDocumentId(Item item) {
        return item.getKey().getName();
    }

    /**
     * 全件検索用のドキュメントを登録 OR 上書き
     * @param user
     * @param item
     * @param imageResources
     * @param lang
     * @param title
     * @param detail
     * @param tagList
     * @throws Exception
     */
    public static void putDocument(
            User user, 
            Item item, 
            ItemGcsRes imageResources,
            String title, 
            String detail, 
            List<String> tagList) throws Exception {


        Document document = Document.newBuilder()
            .setId(getDocumentId(item))
            .addField(Field.newBuilder()
                .setName("userKeyId")
                .setAtom(String.valueOf(user.getKey().getId())))
            .addField(Field.newBuilder()
                .setName("itemKeyId")
                .setAtom(item.getKey().getName()))
            .addField(Field.newBuilder()
                .setName("tags")
                .setText(tagList.toString()))
            .addField(Field.newBuilder()
                .setName("title")
                .setText(title))
            .addField(Field.newBuilder()
                .setName("detail")
                .setText(detail))
            .addField(Field.newBuilder()
                .setName("contentType")
                .setText(imageResources.getContentType()))
            .addField(Field.newBuilder()
                .setName("width")
                .setNumber(imageResources.getWidth()))
            .addField(Field.newBuilder()
                .setName("height")
                .setNumber(imageResources.getHeight()))
            .addField(Field.newBuilder()
                .setName("published")
                .setDate(item.getCreateDate()))
            .addField(Field.newBuilder()
                .setName("sortOrder")
                .setAtom(String.valueOf(item.getSortOrder())))
        .build();

        Index index = getDocumentIndex();
        
        System.out.println(item.getSortOrder());

        index.put(document);
    }
    
    /**
     * 全件検索検索
     * @param userModel
     * @param content
     */
    public static List<Item> searchByKeyword(String qstrString) throws Exception {
        
        if(StringUtil.isEmpty(qstrString)) throw new NullPointerException();
        
        String qstr = qstrString;
        
        Index index = getDocumentIndex();
        
        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(App.KEYWORD_SEARCH_ITEM_LIST_LIMIT)
                    .setSortOptions(SortOptions.newBuilder()
                    .addSortExpression(SortExpression.newBuilder()
                        .setExpression("published")
                        .setDefaultValueDate(new Date())
                        .setDirection(SortDirection.DESCENDING)))
                    .build())
                    .build(qstr);
        Results<ScoredDocument> results = index.search(query);
        
        return getItemListByResults(results);
        
    }
    
    /**
     * タグによるドキュメントの検索
     * @param userModel
     * @param content
     */
    private static Results<ScoredDocument> searchByTag(User user, String tag) throws Exception {
        if(StringUtil.isEmpty(tag)) throw new NullPointerException();
        
        // クリエ毎のカーソルを使用
        Cursor cursor = Cursor.newBuilder().setPerResult(false).build();
        
        String qstr = "tags:\"" + tag + "\"" 
                + " AND userKeyId=\"" + user.getKey().getId()  + "\"";
        
        Index index = getDocumentIndex();
        
        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(App.USER_PORTFOLIO_ITEM_LIST_LIMIT)
                    .setSortOptions(SortOptions.newBuilder()
                        .addSortExpression(SortExpression.newBuilder()
                            .setExpression("sortOrder")
                            .setDirection(SortDirection.ASCENDING)))
                    .setCursor(cursor)
                    .build())
                    .build(qstr);
        Results<ScoredDocument> results = index.search(query);
        
        return results;
    }
    
    /**
     * タグ検索
     * @param user
     * @param tag
     * @param cursorString
     * @return
     * @throws Exception
     */
    public static Results<ScoredDocument> searchByTag(User user, String tag, String cursorString) throws Exception {
        if (StringUtil.isEmpty(cursorString)) return searchByTag(user, tag);
        
        Cursor cursor = Cursor.newBuilder().setPerResult(false).build(cursorString);
        
        String qstr = "tags:\"" + tag + "\"" 
                + " AND userKeyId=\"" + user.getKey().getId()  + "\"";
        
        Index index = getDocumentIndex();
        
        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(App.USER_PORTFOLIO_ITEM_LIST_LIMIT)
                    .setSortOptions(SortOptions.newBuilder()
                        .addSortExpression(SortExpression.newBuilder()
                            .setExpression("sortOrder")
                            .setDirection(SortDirection.ASCENDING)))
                    .setCursor(cursor)
                    .build())
                    .build(qstr);
        Results<ScoredDocument> results = index.search(query);
        
        return results;
        
    }
    
    /**
     * 検索結果からアイテムリストを生成
     * @param results
     * @param lang
     * @return
     */
    public static List<Item> getItemListByResults(Results<ScoredDocument> results) {
        // 対象のアイテムリストを取得
        List<Item> itemList = new ArrayList<Item>();
        for (ScoredDocument document : results) {
            String itemKey = document.getOnlyField("itemKeyId").getAtom();
            
            if(!StringUtil.isEmpty(itemKey)) {
                Item item = ItemService.getByKey(itemKey);
                
                if(item != null) {
                    itemList.add(item);
                }
            }
        }
        
        return itemList;
    }
    
    /**
     * documentId からドキュメントを取得
     * @param item
     * @param lang
     * @return
     */
    public static Document getDocumentById(Item item) {
        String documentId = getDocumentId(item);
        
        Index index = getDocumentIndex();
        
        return index.get(documentId);
    }
    
    /**
     * アイテムのタグ配列を取得
     * @param item
     * @param lang
     * @return
     */
    public static List<String> getItemTags(Item item) {
        Document document = getDocumentById(item);

        try {
            String tags = document.getOnlyField("tags").getText().replaceAll("^\\[", "").replaceAll("\\]$", "");

            if(StringUtil.isEmpty(tags)) return new ArrayList<String>();
            
            List<String> tagList = new ArrayList<String>();
            String[] tagArray = tags.split(",");
            for(String tag: tagArray) {
                if(!StringUtil.isEmpty(tag)) tagList.add(tag.trim());
            }
            
            return tagList;
            
        }catch(Exception e) {
            return null;
        }
    }
    
    /**
     * アイテムのドキュメントを削除
     * @param item
     * @param lang
     * @return
     */
    public static void deleteItemDocument(Item item) {
        Document document = getDocumentById(item);
        
        Index index = getDocumentIndex();
        
        index.delete(document.getId());
    }

}
