package com.pictby.dao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GcsResourcesDaoTest extends AppEngineTestCase {

    private GcsResourcesDao dao = new GcsResourcesDao();

    @Test
    public void test() throws Exception {
        assertThat(dao, is(notNullValue()));
    }
}
