package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class TagAddEntryController extends BaseController {
    
    @Override
    protected Navigation execute(User user) throws Exception {
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String tag = asString("tag");
        String itemId = asString("itemId");
        
        // 入力チェック
        if(StringUtil.isEmpty(tag) && StringUtil.isEmpty(itemId)) {
            requestScope("status", "NG");
            return forward("/user/ajax_response.jsp");
        }
        
        // アイテムが存在しない、もしくは既にアイテムに登録されているタグの場合は何もしない
        Item item = ItemService.getByKey(itemId);
        if(item == null) {
            requestScope("status", "NG");
            return forward("/user/ajax_response.jsp");
        }

        // タグの追加処理
        ItemService.itemAddTag(user, item, tag);
        
        
        requestScope("status", "OK");
        return forward("/user/ajax_response.jsp");
    }
}
