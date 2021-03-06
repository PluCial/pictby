package com.pictby.controller.user.secure;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ItemDeleteControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/user/secure/itemDelete");
        ItemDeleteController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/user/secure/itemDelete.jsp"));
    }
}
