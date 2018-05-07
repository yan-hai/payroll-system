package com.nobodyhub.payroll.core.task;

/**
 * Factory to get the Task definition
 *
 * @author Ryan
 */
public interface TaskFactory {
    /**
     * Get {@link Task} of given <code>taskId</code>
     *
     * @param taskId
     * @return
     */
    Task get(String taskId);
}
