package com.nobodyhub.payroll.core.context;

import com.nobodyhub.payroll.core.context.NormalFormulaContext;
import com.nobodyhub.payroll.core.context.RetroFormulaContext;
import com.nobodyhub.payroll.core.item.ItemFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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