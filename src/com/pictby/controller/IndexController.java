package com.pictby.controller;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pictby.App;
import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;
import com.pictby.service.UserService;

public class IndexController extends BaseController {

    @Override
    protected Navigation run() throws Exception {
        User loginUser = sessionScope("loginUser");
        if(loginUser != null) {
            requestScope("loginUser", loginUser);
            requestScope("isLogged", String.valueOf(true));
        }
        
        // 新着ユーザーリスト
        List<User> newUserList = UserService.getNew(App.TOP_NEW_CREATOR_LIST_LIMIT);
        requestScope("newUserList", newUserList == null ? new ArrayList<User>() : newUserList);
        
        // 新着画像リスト
        List<Item> newItemList = ItemService.getNew(App.TOP_NEW_PORTFOLIO_ITEM_LIST_LIMIT, null);
        requestScope("newItemList", newItemList == null ? new ArrayList<Item>() : newItemList);
        
        return forward("index.jsp");
    }
}
