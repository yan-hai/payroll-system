package com.nobodyhub.payroll.core.item.payment.rounding;

import com.nobodyhub.payroll.core.item.payment.rounding.abstr.RoundingProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author yan_h
 * @since 2018-05-07.
 */
public enum RoundingRule {
    /**
     * no rounding rule applied
     */
    NA(before -> before),
    /**
     * Precision: 0, Rounding Mode: HALF_UP
     */
    P0_HALF_UP((before -> before.setScale(0, RoundingMode.HALF_UP)))

    /**
     * TODO: add all existing rounding rules
     */
    ;

    private RoundingProcessor handler;

    RoundingRule(RoundingProcessor handler) {
        this.handler = handler;
    }

    public BigDecimal round(BigDecimal before) {
        return handler.round(before);
    }
}
