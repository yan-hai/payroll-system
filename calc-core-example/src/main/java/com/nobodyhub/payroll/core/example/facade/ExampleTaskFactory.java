package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.task.ExecutionCallbackFactory;
import com.nobodyhub.payroll.core.task.Task;
import com.nobodyhub.payroll.core.task.TaskFactory;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleTaskFactory extends TaskFactory {

    public ExampleTaskFactory(ItemBuilderFactory itemBuilderFactory,
                              NormalFormulaFactory normalFormulaFactory,
                              RetroFormulaFactory retroFormulaFactory,
                              ProrationFactory prorationFactory,
                              ExecutionCallbackFactory executionCallbackFactory) {
        super(itemBuilderFactory, normalFormulaFactory, retroFormulaFactory, prorationFactory, executionCallbackFactory);
    }

    /**
     * get Proration from container
     *
     * @param id
     * @return
     */
    @Override
    public Task get(String id) {
        Task task = super.get(id);
        task.setExecutionCallback(executionCallbackFactory.get());
        return task;
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new Task("Task_1",
                itemBuilderFactory,
                normalFormulaFactory,
                retroFormulaFactory,
                prorationFactory
        ));
    }
}
