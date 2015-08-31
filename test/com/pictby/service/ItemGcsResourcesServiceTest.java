package com.pictby.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ItemGcsResourcesServiceTest extends AppEngineTestCase {

    private GcsItemResourcesService service = new GcsItemResourcesService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
