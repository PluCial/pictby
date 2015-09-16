package com.pictby.controller.user.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;

import com.pictby.model.User;
import com.pictby.service.GcsUserResourcesService;
import com.pictby.service.MemcacheService;

public class ChangeProfileImageEntryController extends BaseController {

    @Override
    protected Navigation execute(User user) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            
            return forward("/user/secure/changeProfileImage.jsp");
        }
        
        // リクエストパラメーターの取得
        FileItem fileItem = requestScope("uploadImage");
        int imageX = asInteger("imageX");
        int imageY = asInteger("imageY");
        int imageWidth = asInteger("imageWidth");
        int imageHeight = asInteger("imageHeight");
        
        if(user.getIconImageResources() == null) {
            GcsUserResourcesService.addUserIconImage(user, fileItem, imageX, imageY, imageX + imageWidth, imageY + imageHeight);
            
        }else {
            String resourcesKey = asString("resourcesKey");
            GcsUserResourcesService.updateUserIconImage(user, resourcesKey, fileItem, imageX, imageY, imageX + imageWidth, imageY + imageHeight);
        }
        
        // キャッシュクリア
        MemcacheService.deleteUser(user.getUserId());
        
        
        return redirect("/" + user.getUserId());
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
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
