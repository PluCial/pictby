package com.pictby.controller;

import java.util.ArrayList;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.pictby.App;
import com.pictby.model.Item;
import com.pictby.service.ItemService;

public class NewItemController extends Controller {

    @Override
    public Navigation run() throws Exception {
        S3QueryResultList<Item> itemList = ItemService.getNew(App.NEW_PORTFOLIO_ITEM_LIST_LIMIT, null);
        requestScope("itemList", itemList == null ? new ArrayList<Item>() : itemList);
        requestScope("cursor", itemList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(itemList.hasNext()));

        return forward("newItem.jsp");
    }
}
