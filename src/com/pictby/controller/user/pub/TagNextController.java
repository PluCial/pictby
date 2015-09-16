package com.pictby.controller.user.pub;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.SearchApiService;

public class TagNextController extends BaseController {

    @Override
    protected Navigation execute(User user, boolean isLogged, boolean isOwner)
            throws Exception {
        
        String tag = asString("tag");
        String cursor = asString("cursor");
        
        if(StringUtil.isEmpty(tag) || StringUtil.isEmpty(cursor)) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("itemListNext.jsp");
        }
        
        Results<ScoredDocument> results = SearchApiService.searchByTag(user, tag, cursor);
        List<Item> itemList = SearchApiService.getItemListByResults(results);
        
        requestScope("itemList", itemList);
        requestScope("hasNext", String.valueOf(results.getCursor() != null));
        
        if(results.getCursor() != null) {
            requestScope("cursor", results.getCursor().toWebSafeString());
        }
        
        return forward("tagNext.jsp");
    }
}
