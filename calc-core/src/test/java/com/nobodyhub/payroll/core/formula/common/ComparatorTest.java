package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class ComparatorTest {
    @Test
    public void testCompare() {
        assertEquals(0, Comparator.EQ.compare(null, null));
        assertEquals(-1, Comparator.EQ.compare(null, ""));
        assertEquals(-1, Comparator.EQ.compare(null, 1));
        assertEquals(-1, Comparator.EQ.compare(null, LocalDate.of(2018, 5, 21)));

        assertEquals(1, Comparator.EQ.compare("", null));
        assertEquals(1, Comparator.EQ.compare(1, null));
        assertEquals(1, Comparator.EQ.compare(LocalDate.of(2018, 5, 21), null));

        assertEquals(0, Comparator.EQ.compare("", ""));
        assertEquals(-1, Comparator.EQ.compare(1, 2));
        assertEquals(1, Comparator.EQ.compare(LocalDate.of(2018, 5, 21), LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testApply() throws PayrollCoreException {
        assertEquals(true, Comparator.EQ.apply(2, 2, null));
        assertEquals(true, Comparator.NE.apply(2, 3, null));

        assertEquals(false, Comparator.O_O.apply(2, 2, 2));
        assertEquals(false, Comparator.O_O.apply(2, 1, 2));
        assertEquals(false, Comparator.O_O.apply(2, 2, 3));
        assertEquals(true, Comparator.O_O.apply(2, 1, 3));

        assertEquals(false, Comparator.O_C.apply(2, 2, 2));
        assertEquals(true, Comparator.O_C.apply(2, 1, 2));
        assertEquals(false, Comparator.O_C.apply(2, 2, 3));
        assertEquals(true, Comparator.O_C.apply(2, 1, 3));

        assertEquals(false, Comparator.C_O.apply(2, 2, 2));
        assertEquals(false, Comparator.C_O.apply(2, 1, 2));
        assertEquals(true, Comparator.C_O.apply(2, 2, 3));
        assertEquals(true, Comparator.C_O.apply(2, 1, 3));

        assertEquals(true, Comparator.C_C.apply(2, 2, 2));
        assertEquals(true, Comparator.C_C.apply(2, 1, 2));
        assertEquals(true, Comparator.C_C.apply(2, 2, 3));
        assertEquals(true, Comparator.C_C.apply(2, 1, 3));
    }
}