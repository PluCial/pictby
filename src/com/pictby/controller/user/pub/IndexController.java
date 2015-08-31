package com.pictby.controller.user.pub;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(User user, boolean isLogged,
            boolean isOwner) throws Exception {
        
        List<Item> itemList = ItemService.getItemList(user, null);
        requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        
        return forward("index.jsp");
    }
}
