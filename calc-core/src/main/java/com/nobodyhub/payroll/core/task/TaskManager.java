package com.nobodyhub.payroll.core.task;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

/**
 * TODO: make it thread-safe
 *
 * @author Ryan
 */
public class TaskManager {
    /**
     * change to use google cache
     */
    private Map<String, Task> tasks = Maps.newHashMap();

    public void register(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    public Task get(String taskId) {
        //TODO: handle null case
        //TODO: if null, get from DB
        return tasks.get(taskId);
    }

    public void clear(Set<String> taskIds) {
        tasks.keySet().removeAll(taskIds);
    }
}
