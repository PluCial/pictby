package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;
import com.pictby.service.MemcacheService;

public class ItemChangeIndexController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        String itemId = asString("itemId");
        String prevId = asString("prevId");
        String nextId = asString("nextId");
        
        // itemId もしくは 前後のアイテムIDが両方ともNull場合はエラー
        if(StringUtil.isEmpty(itemId)
                || (StringUtil.isEmpty(prevId) && StringUtil.isEmpty(nextId))
                ) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }
        
        // 前後のアイテムIDが同じの場合エラー
        if(!StringUtil.isEmpty(prevId) && !StringUtil.isEmpty(nextId) && prevId.equals(nextId)) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }
        
        
        Item targetItem = ItemService.getByKey(itemId);
        double order = targetItem.getSortOrder();
        
        // 前後にアイテムが存在する場合 平均値を設定
        if(!StringUtil.isEmpty(prevId) && !StringUtil.isEmpty(nextId)) {
            Item prevItem = ItemService.getByKey(prevId);
            Item nextItem = ItemService.getByKey(nextId);
            
            order = (prevItem.getSortOrder() + nextItem.getSortOrder()) / 2;
            
        }else if(!StringUtil.isEmpty(prevId)) {
            // 前のアイテムのみ存在する場合(一番最後に移動)
            Item prevItem = ItemService.getByKey(prevId);
            order = prevItem.getSortOrder() + 0.00000001;
            
            
        }else if(!StringUtil.isEmpty(nextId)) {
            // 後のアイテムのみ存在する(一番最初に移動)
            Item nextItem = ItemService.getByKey(nextId);
            order = nextItem.getSortOrder() - 0.00000001;
        }
        
        // 保存処理
        ItemService.changeSortOrder(targetItem, order);
        
        // キャッシュクリア
        MemcacheService.deleteItem(itemId);
        
        
        requestScope("status", "OK");
        return forward("/user/ajax_response.jsp");
    }
}
