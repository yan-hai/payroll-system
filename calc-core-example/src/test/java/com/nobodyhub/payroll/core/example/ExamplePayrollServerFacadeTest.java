package com.nobodyhub.payroll.core.example;

import com.nobodyhub.payroll.core.PayrollCoreServerFacade;
import com.nobodyhub.payroll.core.service.server.PayrollCoreServer;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

/**
 * @author yan_h
 * @since 2018/6/2
 */
public class ExamplePayrollServerFacadeTest {
    private PayrollCoreServerFacade serverFacade;

    private PayrollCoreServer server;

    @Before
    public void setup() throws IOException {
        serverFacade = new ExamplePayrollServerFacade();
        server = serverFacade.server();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
}