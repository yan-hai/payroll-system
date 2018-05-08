package com.nobodyhub.payroll.core.item.payment.rounding.abstr;

import java.math.BigDecimal;

/**
 * Interface of a processor for rounding rule
 *
 * @author yan_h
 * @since 2018-05-07.
 */
public interface RoundingProcessor {
    /**
     * Round value
     *
     * @param before
     * @return
     */
    BigDecimal round(BigDecimal before);
}
