package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.formula.FormulaContext;
import lombok.Builder;
import lombok.Getter;

import java.math.MathContext;

/**
 * @author Ryan
 */
@Builder
@Getter
public abstract class TaskContext {
    protected MathContext mathContext;
    protected FormulaContext formulaContext;
}
