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

public class KwsNextController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        
        User loginUser = sessionScope("loginUser");
        if(loginUser != null) {
            requestScope("loginUser", loginUser);
            requestScope("isLogged", String.valueOf(true));
        }
        
        String keyword = asString("keyword");
        String cursor = asString("cursor");
        if(StringUtil.isEmpty(keyword) || StringUtil.isEmpty(cursor)) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("kwsNext.jsp");
        }

        Results<ScoredDocument> results = SearchApiService.searchByKeyword(keyword, cursor);
        List<Item> itemList = SearchApiService.getItemListByResults(results);
        
        if(itemList == null) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("kwsNext.jsp");
        }
        
        requestScope("itemList", itemList);
        requestScope("hasNext", String.valueOf(results.getCursor() != null));
        
        if(results.getCursor() != null) {
            requestScope("cursor", results.getCursor().toWebSafeString());
        }
        
        return forward("kwsNext.jsp");
    }
}
