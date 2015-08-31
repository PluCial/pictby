package com.pictby.controller;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.exception.NoContentsException;
import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.SearchApiService;

public class KwsController extends BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        User loginUser = sessionScope("loginUser");
        if(loginUser != null) {
            requestScope("loginUser", loginUser);
            requestScope("isLogged", String.valueOf(true));
        }
        
        String keyword = asString("keyword");
        if(StringUtil.isEmpty(keyword)) throw new NoContentsException();

        List<Item> itemList = SearchApiService.searchByKeyword(keyword);
        requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        
        return forward("keyword.jsp");
    }
}
