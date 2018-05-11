package com.nobodyhub.payroll.core;

import com.nobodyhub.payroll.core.service.client.PayrollCoreClient;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public interface PayrollCoreClientFacade {
    /**
     * Client
     *
     * @see com.nobodyhub.payroll.core.service.client.PayrollCoreClientService
     */
    PayrollCoreClient client();


}
