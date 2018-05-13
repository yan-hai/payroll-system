package com.nobodyhub.payroll.core.item.payment.rounding;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rounding Rule
 *
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
    P0_HALF_UP((before -> before.setScale(0, RoundingMode.HALF_UP))),
    /**
     * Precision: 0, Rounding Mode: HALF_DOWN
     */
    P0_HALF_DOWN((before -> before.setScale(0, RoundingMode.HALF_DOWN))),
    /**
     * Precision: 0, Rounding Mode: HALF_UP
     */
    P1_HALF_UP((before -> before.setScale(1, RoundingMode.HALF_UP))),
    /**
     * Precision: 0, Rounding Mode: HALF_UP
     */
    P3_HALF_DOWN((before -> before.setScale(3, RoundingMode.HALF_DOWN))),

    /**
     * TODO: add all existing rounding rules
     */
    ;

    /**
     * the processor for rounding
     */
    private RoundingProcessor processor;

    RoundingRule(RoundingProcessor processor) {
        this.processor = processor;
    }

    public BigDecimal round(BigDecimal before) {
        return processor.round(before);
    }

    /**
     * interface for a rounding processor
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

}
