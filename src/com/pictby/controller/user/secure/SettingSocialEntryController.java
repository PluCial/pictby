package com.pictby.controller.user.secure;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.enums.Social;
import com.pictby.model.SocialLink;
import com.pictby.model.User;
import com.pictby.service.UserService;

public class SettingSocialEntryController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        List<SocialLink> socialLinkList = new ArrayList<SocialLink>();

        for(Social soceal: Social.values()) {
            String link = asString(soceal.toString());
            
            if(!StringUtil.isEmpty(link.trim())) {
                socialLinkList.add(new SocialLink(soceal, link));
            }
        }
        
        UserService.settingSocial(user, socialLinkList);
        
        return redirect("/" + user.getUserId());
    }
}
