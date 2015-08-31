package com.pictby.dao;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.datastore.Sort;
import org.slim3.util.StringUtil;

import com.pictby.meta.ItemMeta;
import com.pictby.model.Item;
import com.pictby.model.User;

public class ItemDao extends DaoBase<Item>{
    
    /** META */
    private static final ItemMeta meta = ItemMeta.get();
    
    /**
     * ユーザーの保有するリストを取得
     * @param user
     * @param lang
     * @return
     */
    private S3QueryResultList<Item> getList(User user, int num) {
        S3QueryResultList<Item> list = Datastore.query(meta)
                .filter(meta.userRef.equal(user.getKey()))
                        .sort(new Sort(meta.sortOrder))
                        .limit(num)
                        .asQueryResultList();

        return list;
    }
    
    /**
     * ユーザーの保有するリストを取得
     * @param user
     * @param lang
     * @return
     */
    public S3QueryResultList<Item> getList(User user, int num, String cursor) {
        if (StringUtil.isEmpty(cursor)) return getList(user, num);
        
        S3QueryResultList<Item> list = Datastore.query(meta)
                .filter(meta.userRef.equal(user.getKey()))
                        .sort(new Sort(meta.sortOrder))
                        .encodedStartCursor(cursor)
                        .limit(num)
                        .asQueryResultList();

        return list;
    }
    
    /**
     * ユーザーの保有するリストを取得
     * @param user
     * @param lang
     * @return
     */
    private S3QueryResultList<Item> getNew(int num) {
        S3QueryResultList<Item> list = Datastore.query(meta)
                        .sort(meta.sortOrder.desc)
                        .limit(num)
                        .asQueryResultList();

        return list;
    }
    
    /**
     * ユーザーの保有するリストを取得
     * @param user
     * @param lang
     * @return
     */
    public S3QueryResultList<Item> getNew(int num, String cursor) {
        if (StringUtil.isEmpty(cursor)) return getNew(num);
        
        S3QueryResultList<Item> list = Datastore.query(meta)
                        .sort(meta.sortOrder.desc)
                        .encodedStartCursor(cursor)
                        .limit(num)
                        .asQueryResultList();

        return list;
    }

}
