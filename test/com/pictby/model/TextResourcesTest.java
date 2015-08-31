package com.pictby.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TextResourcesTest extends AppEngineTestCase {

    private UserTextRes model = new UserTextRes();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
