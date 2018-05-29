package com.nobodyhub.payroll.core.util;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONVERTER_NOT_FOUND;
import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class ValueConverterTest {
    @Test
    public void testConvertFromString() throws PayrollCoreException {
        assertEquals(null,
                ValueConverter.convertFromString(null, BigDecimal.class));
        assertEquals("string",
                ValueConverter.convertFromString("string", String.class));
        assertEquals(new BigDecimal("123"),
                ValueConverter.convertFromString("123", BigDecimal.class));
        assertEquals(LocalDate.of(2018, 5, 28),
                ValueConverter.convertFromString("20180528", LocalDate.class));
        assertEquals(LocalDateTime.of(2018, 5, 28, 20, 34, 15),
                ValueConverter.convertFromString("20180528203415", LocalDateTime.class));
        assertEquals(true,
                ValueConverter.convertFromString("true", Boolean.class));
        assertEquals(LocalTime.of(20, 34, 15),
                ValueConverter.convertFromString("203415", LocalTime.class));
    }

    @Test(expected = PayrollCoreException.class)
    public void testConvertFromString_() throws PayrollCoreException {
        try {
            ValueConverter.convertFromString("203415", ValueConverterTest.class);
        } catch (PayrollCoreException e) {
            assertEquals(CONVERTER_NOT_FOUND, e.getCode());
            throw e;
        }
    }

    @Test
    public void testConvertToString() throws PayrollCoreException {
        assertEquals(null,
                ValueConverter.convertToString(null));
        assertEquals("string",
                ValueConverter.convertToString("string"));
        assertEquals("123",
                ValueConverter.convertToString(new BigDecimal("123")));
        assertEquals("20180528",
                ValueConverter.convertToString(LocalDate.of(2018, 5, 28)));
        assertEquals("20180528203415",
                ValueConverter.convertToString(LocalDateTime.of(2018, 5, 28, 20, 34, 15)));
        assertEquals("true",
                ValueConverter.convertToString(true));
        assertEquals("203415",
                ValueConverter.convertToString(LocalTime.of(20, 34, 15)));
    }

    @Test(expected = PayrollCoreException.class)
    public void testConvertToString_() throws PayrollCoreException {
        try {
            ValueConverter.convertToString(new ValueConverterTest());
        } catch (PayrollCoreException e) {
            assertEquals(CONVERTER_NOT_FOUND, e.getCode());
            throw e;
        }
    }

}