package com.nobodyhub.payroll.core.item.hr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class HrBooleanItemTest {
    @Test
    public void testDefaultValue() {
        HrBooleanItem item = new HrBooleanItem("item");
        assertEquals(false, item.defaultValue());
        assertEquals("item", item.build().getId());
    }
}