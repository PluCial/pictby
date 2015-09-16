package com.pictby.controller.user.pub;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(User user, boolean isLogged,
            boolean isOwner) throws Exception {
        
        S3QueryResultList<Item> itemList = ItemService.getItemList(user, null);
        
        if(itemList == null) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("index.jsp");
        }
        
        requestScope("itemList", itemList);
        requestScope("cursor", itemList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(itemList.hasNext()));
        
        return forward("index.jsp");
    }
}
