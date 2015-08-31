package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;

import com.pictby.model.ItemTextRes;
import com.pictby.model.User;
import com.pictby.service.TextItemResourcesService;

public class EditTextItemResourcesController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        String resourcesKey = asString("resourcesKey");
        
        ItemTextRes textResources =  TextItemResourcesService.getResources(resourcesKey);
        requestScope("textResources", textResources);
        
        return forward("editTextItemResources.jsp");
        
    }
}
