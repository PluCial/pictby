package com.pictby.controller.user.secure;

import java.util.List;

import org.slim3.controller.Navigation;

import com.pictby.model.ItemTag;
import com.pictby.model.User;

public class UserTagsController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        List<ItemTag> itemTagList = user.getItemTagList();
        
        StringBuilder tagsResourcesString = new StringBuilder();
        
        for(int i = 0; i < itemTagList.size(); i++) {
            ItemTag tag = itemTagList.get(i);
            tagsResourcesString.append("\"");
            tagsResourcesString.append(tag.getTagName());
            tagsResourcesString.append("\"");
            
            if(i < itemTagList.size() -1) {
                tagsResourcesString.append(",");
            }
        }
        
        requestScope("tagsResourcesString", tagsResourcesString.toString());
        
        return forward("userTags.jsp");
    }
}
