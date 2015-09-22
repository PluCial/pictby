package com.pictby.controller;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.util.StringUtil;

import com.pictby.App;
import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;

public class NewItemNextController extends Controller {

    @Override
    protected Navigation run() throws Exception {        
        String cursor = asString("cursor");
        if(StringUtil.isEmpty(cursor)) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("newItemNext.jsp");
        }
        
        S3QueryResultList<Item> itemList = ItemService.getNew(App.NEW_PORTFOLIO_ITEM_LIST_LIMIT, cursor);
//        S3QueryResultList<Item> itemList = ItemService.getItemList(user, cursor);
        if(itemList == null) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("newItemNext.jsp");
        }
        
        requestScope("itemList", itemList);
        requestScope("cursor", itemList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(itemList.hasNext()));
        
        return forward("newItemNext.jsp");
    }
}
