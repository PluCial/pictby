package com.pictby.controller.info;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ContactControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/info/contact");
        ContactController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/info/contact.jsp"));
    }
}
