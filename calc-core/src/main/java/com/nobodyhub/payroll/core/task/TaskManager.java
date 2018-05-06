package com.nobodyhub.payroll.core.task;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Ryan
 */
public class TaskManager {
    private Map<String, Task> tasks = Maps.newHashMap();

    public void register(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    public void get(String taskId) {
        //TODO: handle null case
        tasks.get(taskId);
    }
}
