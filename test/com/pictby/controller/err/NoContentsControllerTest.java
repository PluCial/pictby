package com.pictby.controller.err;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class NoContentsControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/err/noContents");
        NoContentsController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/err/noContents.jsp"));
    }
}
