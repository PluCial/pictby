package com.pictby.dao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserGcsResourcesDaoTest extends AppEngineTestCase {

    private UserGcsResDao dao = new UserGcsResDao();

    @Test
    public void test() throws Exception {
        assertThat(dao, is(notNullValue()));
    }
}
