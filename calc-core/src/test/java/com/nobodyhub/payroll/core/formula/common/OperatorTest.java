package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class OperatorTest {
    @Test
    public void testApply() throws PayrollCoreException {
        assertEquals(new BigDecimal("2.3"), Operator.ADD.apply(new BigDecimal("1.1"), new BigDecimal("1.2")));
        assertEquals(new BigDecimal("0.9"), Operator.SUB.apply(new BigDecimal("2.1"), new BigDecimal("1.2")));
        assertEquals(new BigDecimal("2.52"), Operator.MUL.apply(new BigDecimal("2.1"), new BigDecimal("1.2")));
        assertEquals(new BigDecimal("0.3333333333333333333333333333333333"), Operator.DIV.apply(new BigDecimal("1"), new BigDecimal("3")));
    }
}