package com.pictby.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GcsResourcesServiceTest extends AppEngineTestCase {

    private GcsResourcesService service = new GcsResourcesService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
