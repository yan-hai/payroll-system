package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.task.execution.TaskExecution;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Calender item for the calculation period, {@link TaskExecution#period}
 *
 * @author yan_h
 * @since 2018-05-09.
 */
public class CalendarItem extends Item<BigDecimal, CalendarItem> implements ProrationHandler {

    public CalendarItem(String itemId) {
        super(itemId);
    }

    public BigDecimal getValue(LocalDate start, LocalDate end) {
        BigDecimal value = BigDecimal.ZERO;
        for (LocalDate key : values.keySet()) {
            if (key.compareTo(start) >= 0
                    && key.compareTo(end) <= 0) {
                value = value.add(values.get(key));
            }
        }
        return value;
    }

    @Override
    public void setStringValue(LocalDate date, String value) {
        values.put(date, new BigDecimal(value));
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public CalendarItem build() {
        return new CalendarItem(itemId);
    }

    @Override
    public BigDecimal prorate(TreeMap<Period, BigDecimal> data) {
        BigDecimal totalVal = getTotalValue();
        BigDecimal finalVal = BigDecimal.ZERO;
        for (Period period : data.keySet()) {
            BigDecimal periodVal = BigDecimal.ZERO;
            for (Map.Entry<LocalDate, BigDecimal> entry : values.entrySet()) {
                LocalDate sDate = entry.getKey();
                if (period.isAfter(sDate)) {
                    // sdate is before period
                    continue;
                }
                if (period.contains(sDate)) {
                    periodVal = periodVal.add(entry.getValue());
                } else {
                    // sdate is after period
                    break;
                }
            }
            finalVal = finalVal.add(
                    data.get(period).multiply(periodVal).divide(totalVal, PayrollCoreConst.MATH_CONTEXT)
            );
        }
        return finalVal;
    }

    public BigDecimal getTotalValue() {
        return values.values().stream().reduce(BigDecimal.ZERO, (a, b) -> (a.add(b)));
    }
}
