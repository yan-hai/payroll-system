package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.task.ExecutionCallbackBuilderFactory;
import com.nobodyhub.payroll.core.task.Task;
import com.nobodyhub.payroll.core.task.TaskFactory;

import static com.nobodyhub.payroll.core.example.facade.ExampleConst.TASK_ID;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleTaskFactory extends TaskFactory {

    public ExampleTaskFactory(ItemBuilderFactory itemBuilderFactory,
                              NormalFormulaFactory normalFormulaFactory,
                              RetroFormulaFactory retroFormulaFactory,
                              ProrationFactory prorationFactory,
                              ExecutionCallbackBuilderFactory executionCallbackBuilderFactory) {
        super(itemBuilderFactory,
                normalFormulaFactory,
                retroFormulaFactory,
                prorationFactory,
                executionCallbackBuilderFactory);
        initContents();
    }



    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new Task(TASK_ID,
                itemBuilderFactory,
                normalFormulaFactory,
                retroFormulaFactory,
                prorationFactory
        ));
    }
}
