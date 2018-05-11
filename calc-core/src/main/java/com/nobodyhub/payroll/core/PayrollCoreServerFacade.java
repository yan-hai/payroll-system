package com.nobodyhub.payroll.core;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClient;
import com.nobodyhub.payroll.core.service.server.PayrollCoreServer;
import com.nobodyhub.payroll.core.task.ExecutionCallbackFactory;
import com.nobodyhub.payroll.core.task.TaskFactory;

/**
 * Facade to prepare class for server
 *
 * @author yan_h
 * @since 2018-05-11
 */
public abstract class PayrollCoreServerFacade {
    /**
     * For items
     *
     * @see com.nobodyhub.payroll.core.item.common.Item
     */
    protected ItemBuilderFactory itemBuilderFactory;
    /**
     * For normal formulas
     *
     * @see com.nobodyhub.payroll.core.formula.normal.NormalFormula
     */
    protected NormalFormulaFactory normalFormulaFactory;
    /**
     * For retroactive formulas
     *
     * @see com.nobodyhub.payroll.core.formula.retro.RetroFormula
     */
    protected RetroFormulaFactory retroFormulaFactory;
    /**
     * For proration rules
     *
     * @see com.nobodyhub.payroll.core.proration.abstr.Proration
     */
    protected ProrationFactory prorationFactory;
    /**
     * For execution callback
     *
     * @see com.nobodyhub.payroll.core.task.callback.Callback
     * @see com.nobodyhub.payroll.core.service.server.PayrollCoreServerService
     */
    protected ExecutionCallbackFactory executionCallbackFactory;

    /**
     * For task
     *
     * @see com.nobodyhub.payroll.core.task.Task
     */
    protected TaskFactory taskFactory;
    /**
     * Client
     *
     * @see com.nobodyhub.payroll.core.service.client.PayrollCoreClientService
     */
    protected PayrollCoreClient client;
    /**
     * Server
     *
     * @see com.nobodyhub.payroll.core.service.server.PayrollCoreServerService
     */
    protected PayrollCoreServer server;


    public ItemBuilderFactory itemBuilderFactory() {
        if (itemBuilderFactory == null) {
            itemBuilderFactory = initExampleItemBuilderFactory();
        }
        return itemBuilderFactory;
    }

    /**
     * initialize the value
     *
     * @return
     */
    protected abstract ItemBuilderFactory initExampleItemBuilderFactory();

    public NormalFormulaFactory normFormulaFactory() {
        if (normalFormulaFactory == null) {
            normalFormulaFactory = initNormalFormulaFactory();
        }
        return normalFormulaFactory;
    }

    protected abstract NormalFormulaFactory initNormalFormulaFactory();

    public RetroFormulaFactory retroFormulaFacotry() {
        if (retroFormulaFactory == null) {
            retroFormulaFactory = initRetroFormulaFactory();
        }
        return retroFormulaFactory;
    }

    protected abstract RetroFormulaFactory initRetroFormulaFactory();

    public ProrationFactory prorationFactory() {
        if (prorationFactory == null) {
            prorationFactory = initProrationFactory();
        }
        return prorationFactory;
    }

    protected abstract ProrationFactory initProrationFactory();


    public ExecutionCallbackFactory executionCallbackFactory() {
        if (executionCallbackFactory == null) {
            executionCallbackFactory = initExecutionCallbackFactory();
        }
        return executionCallbackFactory;
    }

    /**
     * initialize the value
     *
     * @return
     */
    protected abstract ExecutionCallbackFactory initExecutionCallbackFactory();

    public TaskFactory taskFactory() {
        if (taskFactory == null) {
            taskFactory = initTaskFactory();
        }
        return taskFactory;
    }

    protected abstract TaskFactory initTaskFactory();

    public PayrollCoreServer server() {
        if (server == null) {
            server = initServer();
        }
        return server;
    }

    protected abstract PayrollCoreServer initServer();
}
