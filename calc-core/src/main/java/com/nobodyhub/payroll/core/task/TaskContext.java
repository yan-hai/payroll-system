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
 * @author Ryan
 */
@Getter
@RequiredArgsConstructor
public abstract class TaskContext {
    protected final ItemFactory itemFactory;
    protected final NormalFormulaContext normalFormulaContext;
    protected final RetroFormulaContext retroFormulaContext;
}
