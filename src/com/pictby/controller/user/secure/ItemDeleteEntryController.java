package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class ItemDeleteEntryController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        String itemId = asString("itemId");
        
        Item item = ItemService.getItemModelOnly(itemId);
        ItemService.deleteItem(user, item);
        
        return redirect("/" + user.getUserId());
    }
}
