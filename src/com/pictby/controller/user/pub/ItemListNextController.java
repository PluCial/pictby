package com.pictby.controller.user.pub;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.util.StringUtil;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class ItemListNextController extends BaseController {

    @Override
    protected Navigation execute(User user, boolean isLogged, boolean isOwner)
            throws Exception {
        
        String cursor = asString("cursor");
        if(StringUtil.isEmpty(cursor)) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("itemListNext.jsp");
        }
        
        S3QueryResultList<Item> itemList = ItemService.getItemList(user, cursor);
        if(itemList == null) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("itemListNext.jsp");
        }
        
        requestScope("itemList", itemList);
        requestScope("cursor", itemList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(itemList.hasNext()));
        
        
        return forward("itemListNext.jsp");
    }
}
