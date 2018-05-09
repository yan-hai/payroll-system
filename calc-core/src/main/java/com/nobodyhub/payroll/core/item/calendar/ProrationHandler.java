package com.nobodyhub.payroll.core.item.calendar;

import java.math.BigDecimal;
import java.util.TreeMap;

/**
 * @author yan_h
 * @since 2018-05-09.
 */
public interface ProrationHandler {

    /**
     * prorate on the values
     *
     * @param values map from effective start date to corresponding value
     * @return
     */
    BigDecimal prorate(TreeMap<Period, BigDecimal> values);
}
