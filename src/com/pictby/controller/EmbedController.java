package com.pictby.controller;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;
import com.pictby.service.UserService;

public class EmbedController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        
        // リクエストパラメーターの取得
        String itemId = asString("itemId");
        if(StringUtil.isEmpty(itemId)) return null;
        
        // アイテムの取得
        Item item = ItemService.getByKey(itemId);
        if(item == null) return null;
        
        User user = UserService.getUser(item.getUserId());
        if(user == null) return null;
        
        requestScope("item", item);
        requestScope("user", user);
        requestScope("domein", getAccessDomeinUrl());
        
        
        return forward("embed.jsp");
    }
}
