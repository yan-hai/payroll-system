package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.formula.FormulaContext;
import com.nobodyhub.payroll.core.item.ItemFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.MathContext;

/**
 * @author Ryan
 */
@Getter
@RequiredArgsConstructor
public abstract class TaskContext {
    protected final MathContext mathContext;
    protected final ItemFactory itemFactory;
    protected final FormulaContext formulaContext;
}
