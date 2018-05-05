package com.nobodyhub.payroll.core.task;

import lombok.Builder;
import lombok.Getter;

import java.math.RoundingMode;

/**
 * @author Ryan
 */
@Builder
@Getter
public class TaskContext {
    private RoundingMode roundingMode;
    private TaskCalendar calender;
}
