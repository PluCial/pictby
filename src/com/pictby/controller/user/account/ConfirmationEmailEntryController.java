package com.pictby.controller.user.account;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pictby.enums.EntryType;
import com.pictby.exception.NoContentsException;
import com.pictby.model.Entry;
import com.pictby.service.ResetPasswordEntryService;
import com.pictby.service.SignupEntryService;

public class ConfirmationEmailEntryController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        String entryId = asString("entryId");
        String type = asString("type");
        
        if(StringUtil.isEmpty(entryId) || StringUtil.isEmpty(type)) {
            throw new NoContentsException();
        }
        
        // エントリータイプの設定
        EntryType entryType = null;
        try {
            entryType = EntryType.valueOf(type);
            
        }catch(Exception e) {
            throw new NoContentsException();
        }
        
        Entry entry = null;
        
        
        // DBからエントリー情報を取得
        if(entryType == EntryType.REGISTER) {
            entry = SignupEntryService.getByKey(entryId);

        }else if(entryType == EntryType.RESET_PASSWORD) {
            entry = ResetPasswordEntryService.getByKey(entryId);
            
        }else {
            throw new NoContentsException();
        }
        
        if(entry == null) throw new NoContentsException();
        
        return redirect(entryType.getRedirectPath() + "?entryId=" + entryId);
    }
}
