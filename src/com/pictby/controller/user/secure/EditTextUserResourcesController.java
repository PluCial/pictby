package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.model.UserTextRes;
import com.pictby.model.User;
import com.pictby.service.TextUserResourcesService;

public class EditTextUserResourcesController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        String resourcesKey = asString("resourcesKey");
        
        UserTextRes textResources = TextUserResourcesService.getResources(resourcesKey);
        requestScope("textResources", textResources);
        
        return forward("editTextUserResources.jsp");
        
    }
}
