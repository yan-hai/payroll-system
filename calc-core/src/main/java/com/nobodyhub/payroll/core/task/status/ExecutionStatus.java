package com.nobodyhub.payroll.core.task.status;

import lombok.Data;

/**
 * Execution status
 *
 * @author Ryan
 */
@Data
public class ExecutionStatus {
    /**
     * status code
     */
    private ExecutionStatusCode statusCode = ExecutionStatusCode.OK;
    /**
     * message, is any, for the abnormal ending
     */
    private String message = "";
}
