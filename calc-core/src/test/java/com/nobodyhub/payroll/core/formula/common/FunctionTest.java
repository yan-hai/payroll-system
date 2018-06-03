package com.nobodyhub.payroll.core.formula.common;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static com.nobodyhub.payroll.core.formula.common.Function.*;
import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 2018/6/3
 */
public class FunctionTest {
    private PaymentItem paymentItem;
    private CalendarItem calendarItem;


    @Before
    public void setup() throws PayrollCoreException {
        paymentItem = new PaymentItem("paymentitem",
                false, null, RoundingRule.NA);
        paymentItem.add(LocalDate.of(2018, 5, 1),
                new BigDecimal("2000"));
        paymentItem.add(LocalDate.of(2018, 5, 11),
                new BigDecimal("1000"));
        paymentItem.add(LocalDate.of(2018, 5, 21),
                new BigDecimal("3500"));


        calendarItem = new CalendarItem("calendarItem");
        calendarItem.addAll(getCalendarValues());
    }

    @Test
    public void testApply() throws PayrollCoreException {
        assertEquals(new BigDecimal("3"), COUNT.apply(paymentItem));
        assertEquals(new BigDecimal("6500"), SUM.apply(paymentItem));
        assertEquals(new BigDecimal("2000"), FIRST.apply(paymentItem));
        assertEquals(new BigDecimal("3500"), LAST.apply(paymentItem));
        assertEquals(new BigDecimal("3500"), MAX.apply(paymentItem));
        assertEquals(new BigDecimal("1000"), MIN.apply(paymentItem));
        assertEquals(new BigDecimal("2166.666666666666666666666666666667"), AVG.apply(paymentItem));

        assertEquals(new BigDecimal("9"), COUNT.apply(calendarItem));
        assertEquals(new BigDecimal("9.3"), SUM.apply(calendarItem));
        assertEquals(new BigDecimal("1"), FIRST.apply(calendarItem));
        assertEquals(new BigDecimal("0.3"), LAST.apply(calendarItem));
        assertEquals(new BigDecimal("2"), MAX.apply(calendarItem));
        assertEquals(new BigDecimal("0"), MIN.apply(calendarItem));
        assertEquals(new BigDecimal("0.62"), AVG.apply(calendarItem));
    }


    private Map<LocalDate, BigDecimal> getCalendarValues() {
        Map<LocalDate, BigDecimal> values = Maps.newTreeMap();
        values.put(LocalDate.of(2018, 5, 1), new BigDecimal("1")); //counted
        values.put(LocalDate.of(2018, 5, 2), new BigDecimal("1")); //counted
        values.put(LocalDate.of(2018, 5, 3), new BigDecimal("0"));
        values.put(LocalDate.of(2018, 5, 4), new BigDecimal("1")); //counted
        values.put(LocalDate.of(2018, 5, 5), new BigDecimal("0"));
        values.put(LocalDate.of(2018, 5, 6), new BigDecimal("2")); //counted
        values.put(LocalDate.of(2018, 5, 7), new BigDecimal("0"));
        values.put(LocalDate.of(2018, 5, 8), new BigDecimal("1")); //counted
        values.put(LocalDate.of(2018, 5, 9), new BigDecimal("0"));
        values.put(LocalDate.of(2018, 5, 10), new BigDecimal("0"));
        values.put(LocalDate.of(2018, 5, 11), new BigDecimal("1.5")); //counted
        values.put(LocalDate.of(2018, 5, 12), new BigDecimal("1")); //counted
        values.put(LocalDate.of(2018, 5, 13), new BigDecimal("0.5")); //counted
        values.put(LocalDate.of(2018, 5, 14), new BigDecimal("0"));
        values.put(LocalDate.of(2018, 5, 15), new BigDecimal("0.3")); //counted
        return values;
    }

}