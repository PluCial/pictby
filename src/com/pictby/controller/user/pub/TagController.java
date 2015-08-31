package com.pictby.controller.user.pub;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.exception.NoContentsException;
import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.SearchApiService;

public class TagController extends BaseController {

    @Override
    protected Navigation execute(User user, boolean isLogged,
            boolean isOwner) throws Exception {
        
        String tag = asString("tag");
        if(StringUtil.isEmpty(tag)) throw new NoContentsException();
        
        List<Item> itemList = SearchApiService.searchByTag(user, tag);
        requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        
        return forward("tag.jsp");
    }
}
