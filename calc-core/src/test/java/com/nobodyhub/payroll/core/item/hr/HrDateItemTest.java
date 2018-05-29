package com.nobodyhub.payroll.core.item.hr;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class HrDateItemTest {
    @Test
    public void testDefaultValue() {
        HrDateItem item = new HrDateItem("item");
        assertEquals(LocalDate.now(), item.defaultValue());
        assertEquals("item", item.build().getId());
    }
}