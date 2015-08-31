package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;

import com.pictby.model.User;
import com.pictby.service.ItemService;

public class ItemAddEntryController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            
            return forward("/user/secure/itemAdd.jsp");
        }
        
        // リクエストパラメーターの取得
        String itemName = asString("itemName");
        String detail = asString("detail");
        
        FileItem fileItem = requestScope("uploadImage");
        int imageX = asInteger("imageX");
        int imageY = asInteger("imageY");
        int imageWidth = asInteger("imageWidth");
        int imageHeight = asInteger("imageHeight");
        
        String tagsinput = asString("tagsinput");
        
        // アイテムの追加
        ItemService.add(user, itemName, detail, tagsinput, fileItem, imageX, imageY, imageWidth, imageHeight);
        
        
        return redirect("/" + user.getUserId());
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // アイテム名
        v.add("itemName", 
            v.required("アイテム名を入力してください。")
        );

        // コンテンツ
        v.add("detail", 
            v.required("アイテムの説明を入力してください。")
        );
        
        // 画像
        v.add("uploadImage", v.required("アイテム画像を添付してください。"));
        
        // 画像情報
        v.add("imageX", v.required());
        v.add("imageY", v.required());
        v.add("imageWidth", v.required());
        v.add("imageHeight", v.required());

        return v.validate();
    }
}
