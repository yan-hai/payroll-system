package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.common.Factory;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;

/**
 * Factory to get the Task definition
 *
 * @author Ryan
 */
public abstract class TaskFactory extends Factory<Task> {
    public TaskFactory(ItemBuilderFactory itemBuilderFactory,
                       NormalFormulaFactory normalFormulaFactory,
                       RetroFormulaFactory retroFormulaFactory,
                       ProrationFactory prorationFactory,
                       ExecutionCallbackBuilderFactory executionCallbackBuilderFactory) {
        this.itemBuilderFactory = itemBuilderFactory;
        this.normalFormulaFactory = normalFormulaFactory;
        this.retroFormulaFactory = retroFormulaFactory;
        this.prorationFactory = prorationFactory;
        this.executionCallbackBuilderFactory = executionCallbackBuilderFactory;
    }

    /**
     * For items
     */
    protected final ItemBuilderFactory itemBuilderFactory;
    /**
     * For normal formulas
     */
    protected final NormalFormulaFactory normalFormulaFactory;
    /**
     * For retroactive formulas
     */
    protected final RetroFormulaFactory retroFormulaFactory;
    /**
     * For proration rules
     */
    protected final ProrationFactory prorationFactory;
    /**
     * For execution callback
     */
    protected final ExecutionCallbackBuilderFactory executionCallbackBuilderFactory;

    /**
     * get Proration from container
     *
     * @param id
     * @return
     */
    @Override
    public Task get(String id) {
        Task task = super.get(id).build();
        task.setExecutionCallback(executionCallbackBuilderFactory.get());
        return task;
    }
}
