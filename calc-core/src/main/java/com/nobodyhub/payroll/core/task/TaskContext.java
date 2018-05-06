package com.nobodyhub.payroll.core.task;

import lombok.Builder;
import lombok.Getter;

import java.math.MathContext;

/**
 * @author Ryan
 */
@Builder
@Getter
public class TaskContext {
    private MathContext mathContext;
}
