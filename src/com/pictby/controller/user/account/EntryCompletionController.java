package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;

import com.pictby.enums.EntryType;

public class EntryCompletionController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        
        String type = asString("type");
        
        requestScope("entry", EntryType.valueOf(type));
        
        
        return forward("entryCompletion.jsp");
    }
}
