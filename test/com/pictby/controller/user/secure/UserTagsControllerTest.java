package com.pictby.controller.user.secure;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserTagsControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/user/secure/userTags");
        UserTagsController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/user/secure/userTags.jsp"));
    }
}
