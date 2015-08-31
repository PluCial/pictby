package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pictby.model.Item;
import com.pictby.model.User;
import com.pictby.service.ItemService;
import com.pictby.service.SearchApiService;
import com.pictby.service.TextItemResourcesService;
import com.pictby.service.TextUserResourcesService;

public class EditTextResourcesEntryController extends BaseController {
    
    @Override
    protected Navigation execute(User user) throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            requestScope("status", "NG");
            return forward("/user/ajax_response.jsp");
        }
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String resourcesKey = asString("resourcesKey");
        String content = asString("content");
        
        String userId = asString("userId");
        String itemId = asString("itemId");
        
        if(StringUtil.isEmpty(userId) && StringUtil.isEmpty(itemId)) {
            requestScope("status", "NG");
            return forward("/user/ajax_response.jsp");
        }


        if(!StringUtil.isEmpty(itemId)) {
            
            TextItemResourcesService.update(itemId, resourcesKey, content);
            
            Item item = ItemService.getByKey(itemId);
            
            // Documentを更新
            SearchApiService.putDocument(user, item, item.getOriginalImageResources(), item.getName(), item.getDetail(), item.getTagsList());
            
        }else {
            TextUserResourcesService.update(user.getUserId(), resourcesKey, content);
            // ログインセッションのUser情報を更新
            user.setTextResources(TextUserResourcesService.getResourcesMap(user));
        }
        
        requestScope("status", "OK");
        return forward("/user/ajax_response.jsp");
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // コンテンツ
        v.add("content", v.required());
        
        return v.validate();
    }
}
