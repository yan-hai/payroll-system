package com.nobodyhub.payroll.core.service.client;

import com.nobodyhub.payroll.core.service.ServiceConst;

import java.util.Map;

/**
 * @author yan_h
 * @since 2018-05-07.
 */
public class PayrollCoreClient {
    private final String taskId;
    private final PayrollCoreClientService service;


    public PayrollCoreClient(String taskId, String host) {
        this(taskId, host, ServiceConst.DEFAULT_PORT);
    }

    public PayrollCoreClient(String taskId, String host, int port) {
        this.taskId = taskId;
        this.service = new PayrollCoreClientService(host, port);
    }

    public Map<String, Map<String, String>> calculate(Map<String, Map<String, String>> beforeVals) throws InterruptedException {
        return service.calculate(taskId, beforeVals);
    }

    public void close() throws Exception {
        if (service != null) {
            this.service.close();
        }
    }
}
