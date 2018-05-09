package com.nobodyhub.payroll.core.service.client;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.service.common.HistoryData;
import com.nobodyhub.payroll.core.service.common.ServiceConst;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;

import java.util.List;
import java.util.Map;

/**
 * Client to call remote calculation
 *
 * @author yan_h
 * @since 2018-05-07.
 */
public class PayrollCoreClient {
    /**
     * service provider for this client
     */
    private final PayrollCoreClientService service;

    public PayrollCoreClient(String host) {
        this(host, ServiceConst.DEFAULT_PORT);
    }

    public PayrollCoreClient(String host, int port) {
        this.service = new PayrollCoreClientService(host, port);
    }

    /**
     * Execute the payroll task with given data
     *
     * @param requestList    request list
     * @return
     * @throws InterruptedException
     */
    public Map<String, Map<String, String>> calculate(List<PayrollCoreProtocol.Request> requestList) throws InterruptedException, PayrollCoreException {
        return service.calculate(requestList);
    }

    /**
     * shutdown the client
     *
     * @throws Exception
     */
    public void shutdown() throws Exception {
        if (service != null) {
            this.service.shutdown();
        }
    }
}
