package com.pictby.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class KwsbController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("kwsb.jsp");
    }
}
