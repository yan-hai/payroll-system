package com.nobodyhub.payroll.core.example;

import com.nobodyhub.payroll.core.PayrollCoreFacade;
import com.nobodyhub.payroll.core.example.facade.*;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.proration.abstr.Proration;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClient;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClientService;
import com.nobodyhub.payroll.core.service.server.PayrollCoreServer;
import com.nobodyhub.payroll.core.service.server.PayrollCoreServerService;
import com.nobodyhub.payroll.core.task.Task;
import com.nobodyhub.payroll.core.task.TaskFactory;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExamplePayrollFacade implements PayrollCoreFacade {
    /**
     * Factory to get formula for normal execution
     *
     * @return
     * @see NormalFormula
     */
    @Override
    public NormalFormulaFactory normFormulaFactory() {
        return new ExampleNormalFormulaFactory();
    }

    /**
     * Factory to get formula for retroavtive execution
     *
     * @return
     * @see RetroFormula
     */
    @Override
    public RetroFormulaFactory retroFormulaFacotry() {
        return new ExampleRetroFormulaFactory();
    }

    /**
     * Factory to create new instance for item
     *
     * @return
     * @see Item
     */
    @Override
    public ItemFactory itemFactory() {
        return new ExampleItemFactory();
    }

    /**
     * Factory to get proration for items
     *
     * @return
     * @see Proration
     */
    @Override
    public ProrationFactory prorationFactory() {
        return new ExampleProrationFactory();
    }

    /**
     * Factory to get task to execute
     *
     * @return
     * @see Task
     */
    @Override
    public TaskFactory taskFactory() {
        return new ExampleTaskFactory();
    }

    /**
     * Create client instance
     *
     * @return
     * @see PayrollCoreClientService
     */
    @Override
    public PayrollCoreClient client() {
        return new PayrollCoreClient();
    }

    /**
     * Create server instance
     *
     * @return
     * @see PayrollCoreServerService
     */
    @Override
    public PayrollCoreServer server() {
        return new PayrollCoreServer(taskFactory());
    }

    /**
     * Create callback for execution
     *
     * @return
     * @see Callback
     * @see PayrollCoreServerService
     */
    @Override
    public ExecutionCallback executionCallback() {
        return new ExecutionCallback();
    }
}
