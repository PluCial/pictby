package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;
import com.pictby.service.MemcacheService;

public class ItemDeleteEntryController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        String itemId = asString("itemId");
        
        Item item = ItemService.getByKey(itemId);
        ItemService.deleteItem(user, item);
        
        // キャッシュクリア
        MemcacheService.deleteItem(itemId);
        MemcacheService.deleteUser(user.getUserId());
        
        return redirect("/" + user.getUserId());
    }
}
