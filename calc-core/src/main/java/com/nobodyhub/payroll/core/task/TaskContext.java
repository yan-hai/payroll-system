package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.formula.RetroFormulaContext;
import com.nobodyhub.payroll.core.formula.NormalFormulaContext;
import com.nobodyhub.payroll.core.item.ItemFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.MathContext;

/**
 * @author Ryan
 */
@Getter
@RequiredArgsConstructor
public abstract class TaskContext {
    //TODO: remove mathContext
    protected final MathContext mathContext;
    protected final ItemFactory itemFactory;
    protected final NormalFormulaContext normalFormulaContext;
    protected final RetroFormulaContext retroFormulaContext;
}
