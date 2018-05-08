package com.nobodyhub.payroll.core.service.client;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.common.HistoryData;
import com.nobodyhub.payroll.core.service.common.ServiceConst;

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
     * @param taskId id of task to be executed
     * @param data   the data provided to the task
     * @return
     * @throws InterruptedException
     */
    public Map<String, Map<String, String>> calculate(String taskId,
                                                      Map<String, Map<String, String>> data,
                                                      HistoryData histories) throws InterruptedException, PayrollCoreException {
        return service.calculate(taskId, data, histories);
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
