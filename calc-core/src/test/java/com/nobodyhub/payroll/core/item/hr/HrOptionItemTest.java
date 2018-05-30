package com.nobodyhub.payroll.core.item.hr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 2018/5/30
 */
public class HrOptionItemTest {
    @Test
    public void testDefaultValue() {
        HrOptionItem item = new HrOptionItem("item");
        assertEquals("0", item.defaultValue());
        assertEquals("item", item.build().getId());
    }
}