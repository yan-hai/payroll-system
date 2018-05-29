package com.nobodyhub.payroll.core.item.hr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class HrStringItemTest {
    @Test
    public void testDefaultValue() {
        HrStringItem item = new HrStringItem("item");
        assertEquals("", item.defaultValue());
        assertEquals("item", item.build().getId());
    }
}