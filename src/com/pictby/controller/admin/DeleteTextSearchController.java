package com.pictby.controller.admin;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.pictby.service.SearchApiService;

/**
 * TODO あとで削除
 * @author tutinoco
 *
 */
public class DeleteTextSearchController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        SearchApiService.deleteAll();
        
        return null;
    }
}
