package com.pictby.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserGcsResourcesServiceTest extends AppEngineTestCase {

    private GcsUserResourcesService service = new GcsUserResourcesService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
