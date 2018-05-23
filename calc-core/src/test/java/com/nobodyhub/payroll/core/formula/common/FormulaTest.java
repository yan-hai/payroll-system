package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Ryan
 */
public abstract class FormulaTest<T extends Formula> {

    protected T formula;

    @Mock
    protected ItemBuilderFactory itemBuilderFactory;

    protected ExecutionContext executionContext;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);
    }

    public abstract void testCompareTo();

    public abstract void testEvaluate() throws PayrollCoreException;

}