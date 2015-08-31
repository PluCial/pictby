package com.pictby.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TextUserResourcesServiceTest extends AppEngineTestCase {

    private TextUserResourcesService service = new TextUserResourcesService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
