package com.pictby.controller.user.pub;

import org.slim3.controller.Navigation;

import com.pictby.exception.NoContentsException;
import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class ItemController extends BaseController {

    @Override
    protected Navigation execute(
            User user, boolean isLogged,
            boolean isOwner) throws Exception {
        
        String itemId = asString("itemId");
        
        Item item = ItemService.getByKey(itemId);
        if(item == null) throw new NoContentsException();
        
        requestScope("item", item);
        
        return forward("item.jsp");
    }
}
