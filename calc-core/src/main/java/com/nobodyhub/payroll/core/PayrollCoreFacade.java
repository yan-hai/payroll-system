package com.nobodyhub.payroll.core;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClient;
import com.nobodyhub.payroll.core.service.server.PayrollCoreServer;
import com.nobodyhub.payroll.core.task.TaskFactory;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public interface PayrollCoreFacade {
    /**
     * Factory to get formula for normal execution
     *
     * @return
     * @see com.nobodyhub.payroll.core.formula.normal.NormalFormula
     */
    NormalFormulaFactory normFormulaFactory();

    /**
     * Factory to get formula for retroavtive execution
     *
     * @return
     * @see com.nobodyhub.payroll.core.formula.retro.RetroFormula
     */
    RetroFormulaFactory retroFormulaFacotry();

    /**
     * Factory to create new instance for item
     *
     * @return
     * @see com.nobodyhub.payroll.core.item.common.Item
     */
    ItemBuilderFactory itemFactory();

    /**
     * Factory to get proration for items
     *
     * @return
     * @see com.nobodyhub.payroll.core.proration.abstr.Proration
     */
    ProrationFactory prorationFactory();

    /**
     * Factory to get task to execute
     *
     * @return
     * @see com.nobodyhub.payroll.core.task.Task
     */
    TaskFactory taskFactory();

    /**
     * Create client instance
     *
     * @return
     * @see com.nobodyhub.payroll.core.service.client.PayrollCoreClientService
     */
    PayrollCoreClient client();

    /**
     * Create server instance
     *
     * @return
     * @see com.nobodyhub.payroll.core.service.server.PayrollCoreServerService
     */
    PayrollCoreServer server();

    /**
     * Create callback for execution
     *
     * @return
     * @see com.nobodyhub.payroll.core.task.callback.Callback
     * @see com.nobodyhub.payroll.core.service.server.PayrollCoreServerService
     */
    ExecutionCallback executionCallback();
}
