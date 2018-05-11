package com.nobodyhub.payroll.core.example;

import com.nobodyhub.payroll.core.PayrollCoreClientFacade;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClient;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClientService;

/**
 * Facade to prepare class for server
 *
 * @author yan_h
 * @since 2018-05-11
 */
public class ExamplePayrollClientFacade implements PayrollCoreClientFacade {
    /**
     * Client
     *
     * @see PayrollCoreClientService
     */
    @Override
    public PayrollCoreClient client() {
        return new PayrollCoreClient();
    }
}
