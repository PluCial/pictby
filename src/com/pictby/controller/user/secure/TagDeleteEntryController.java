package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;
import com.pictby.service.MemcacheService;

public class TagDeleteEntryController extends BaseController {
    
    @Override
    protected Navigation execute(User user) throws Exception {
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String tag = asString("tag");
        String itemId = asString("itemId");
        
        // 入力チェック
        if(StringUtil.isEmpty(tag.trim()) && StringUtil.isEmpty(itemId)) {
            requestScope("status", "NG");
            return forward("/user/ajax_response.jsp");
        }
        
        // アイテムが存在しない、もしくは既にアイテムに登録されているタグの場合は何もしない
        Item item = ItemService.getByKey(itemId);
        if(item == null) {
            requestScope("status", "NG");
            return forward("/user/ajax_response.jsp");
        }

        // タグの削除処理
        ItemService.itemDeleteTag(user, item, tag.trim());
        
        // キャッシュクリア
        MemcacheService.deleteUser(user.getUserId());
        MemcacheService.deleteItem(item.getKey().getName());
        
        requestScope("status", "OK");
        return forward("/user/ajax_response.jsp");
    }
}
