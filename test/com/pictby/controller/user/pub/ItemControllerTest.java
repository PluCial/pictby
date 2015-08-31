package com.pictby.controller.user.pub;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ItemControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/user/pub/item");
        ItemController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/user/pub/item.jsp"));
    }
}
