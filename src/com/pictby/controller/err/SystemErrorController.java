package com.pictby.controller.err;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class SystemErrorController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("systemError.jsp");
    }
}
