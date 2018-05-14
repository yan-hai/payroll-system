package com.nobodyhub.payroll.core.example;

import com.nobodyhub.payroll.core.PayrollCoreServerFacade;
import com.nobodyhub.payroll.core.example.facade.*;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.server.PayrollCoreServer;
import com.nobodyhub.payroll.core.task.ExecutionCallbackFactory;
import com.nobodyhub.payroll.core.task.TaskFactory;

/**
 * acade to prepare class for server
 *
 * @author yan_h
 * @since 2018-05-11
 */
public class ExamplePayrollServerFacade extends PayrollCoreServerFacade {

    @Override
    public NormalFormulaFactory initNormalFormulaFactory() {
        return new ExampleNormalFormulaFactory(itemBuilderFactory());
    }

    @Override
    public RetroFormulaFactory initRetroFormulaFactory() {
        return new ExampleRetroFormulaFactory(itemBuilderFactory());
    }

    @Override
    public ItemBuilderFactory initExampleItemBuilderFactory() {
        return new ExampleItemBuilderFactory(prorationFactory());
    }

    @Override
    public ProrationFactory initProrationFactory() {
        return new ExampleProrationFactory();
    }

    @Override
    public TaskFactory initTaskFactory() {
        return new ExampleTaskFactory(
                itemBuilderFactory(),
                normFormulaFactory(),
                retroFormulaFacotry(),
                prorationFactory(),
                executionCallbackFactory()
        );
    }

    @Override
    public ExecutionCallbackFactory initExecutionCallbackFactory() {
        return new ExecutionCallbackFactory();
    }

    @Override
    public PayrollCoreServer initServer() {
        return new PayrollCoreServer(taskFactory());
    }
}
