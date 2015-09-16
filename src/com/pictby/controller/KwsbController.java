package com.pictby.controller;

import org.slim3.controller.Navigation;

/**
 * 検索ボックスの表示
 * @author takahara
 *
 */
public class KwsbController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        return forward("kwsb.jsp");
    }
}
