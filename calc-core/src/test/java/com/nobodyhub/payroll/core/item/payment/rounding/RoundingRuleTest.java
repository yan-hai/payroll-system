package com.nobodyhub.payroll.core.item.payment.rounding;

import org.junit.Test;

import java.math.BigDecimal;

import static com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class RoundingRuleTest {
    @Test
    public void test() {
        assertEquals(new BigDecimal("1.23456"), NA.round(new BigDecimal("1.23456")));
        assertEquals(new BigDecimal("1"), P0_HALF_UP.round(new BigDecimal("1.23456")));
        assertEquals(new BigDecimal("1"), P0_HALF_DOWN.round(new BigDecimal("1.23456")));
        assertEquals(new BigDecimal("1.2"), P1_HALF_UP.round(new BigDecimal("1.23456")));
        assertEquals(new BigDecimal("1.235"), P3_HALF_DOWN.round(new BigDecimal("1.23456")));
    }

}