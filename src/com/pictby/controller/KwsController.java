package com.pictby.controller;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
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
        if(StringUtil.isEmpty(keyword)) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("keyword.jsp");
        }

        Results<ScoredDocument> results = null;
        List<Item> itemList = null;
        try {
            results = SearchApiService.searchByKeyword(keyword, null);
            itemList = SearchApiService.getItemListByResults(results);
        }catch(Exception e) { 
            requestScope("itemList", new ArrayList<Item>());
            return forward("keyword.jsp");
        }
        
        if(itemList == null) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("keyword.jsp");
        }
        
        requestScope("itemList", itemList);
        requestScope("hasNext", String.valueOf(results.getCursor() != null));
        
        if(results.getCursor() != null) {
            requestScope("cursor", results.getCursor().toWebSafeString());
        }
        
        return forward("keyword.jsp");
    }
}
