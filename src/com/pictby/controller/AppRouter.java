package com.pictby.controller;

import org.slim3.controller.router.RouterImpl;

/**
 * 公開部分ルーティング
 * addRoutingメソッドの順番変更は禁止！
 * @author takahara
 *
 */
public class AppRouter extends RouterImpl {

	public AppRouter() {

	    // 検索結果画面
        addRouting(
                "/kws",
                "/kws");
        
        addRouting(
            "/kwsnt",
            "/kwsNext");

        // 検索ダイアログボックス
        addRouting(
                "/kwsb",
                "/kwsb");

        addRouting(
                "/user/userTags/{userId}/{token}",
                "/user/secure/userTags?userId={userId}");
        
        // 埋め込みタグ
        addRouting(
                "/embed/{itemId}",
                "/embed?itemId={itemId}");
        addRouting(
            "/emJs",
            "/embedJs");
	    
	    setUser();
	}
	
	/**
	 * ユーザー
	 */
	private void setUser() {
        
	    // User TOP
        addRouting(
                "/{userId}",
                "/user/pub/index?userId={userId}");
        
        // ITEM LIST
        addRouting(
            "/{userId}/itemList",
            "/user/pub/itemList?userId={userId}");
        
        addRouting(
            "/{userId}/itemListNext",
            "/user/pub/itemListNext?userId={userId}");
        
        // ITEM
        addRouting(
            "/{userId}/item/{itemId}",
            "/user/pub/item?userId={userId}&itemId={itemId}");
        
        // TAG
        addRouting(
            "/{userId}/tag/{tag}",
            "/user/pub/tag?userId={userId}&tag={tag}");
        
        addRouting(
            "/{userId}/tag/{tag}/tagNext",
            "/user/pub/tagNext?userId={userId}&tag={tag}");
	}

}
