package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.formula.NormalFormulaContext;
import com.nobodyhub.payroll.core.formula.RetroFormulaContext;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.MathContext;

import static java.math.MathContext.DECIMAL128;

/**
 * Context for task execution
 * @author Ryan
 */
@Getter
@RequiredArgsConstructor
public abstract class TaskContext {
    /**
     * Item factory to provide the item instance
     */
    protected final ItemFactory itemFactory;
    /**
     * normal formulas
     */
    protected final NormalFormulaContext normalFormulaContext;
    /**
     * retroactive formulas
     */
    protected final RetroFormulaContext retroFormulaContext;
}
