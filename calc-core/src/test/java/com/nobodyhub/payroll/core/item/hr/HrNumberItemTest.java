package com.nobodyhub.payroll.core.item.hr;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class HrNumberItemTest {
    @Test
    public void testDefaultValue() {
        HrNumberItem item = new HrNumberItem("item");
        assertEquals(BigDecimal.ZERO, item.defaultValue());
        assertEquals("item", item.build().getId());
    }
}