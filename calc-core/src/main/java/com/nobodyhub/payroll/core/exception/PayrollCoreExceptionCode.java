package com.nobodyhub.payroll.core.exception;

import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.common.Operator;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public enum PayrollCoreExceptionCode {
    /**
     * Unimplemented handler for {@link Comparator}
     */
    COMPARATOR_UNIMPLEMENTED(Comparator.class, ""),
    /**
     * Unimplemented handler for {@link Operator}
     */
    OPERATOR_UNIMPLEMENTED(Operator.class, ""),
    ;

    private Class<?> clazz;
    private String msgId;

    PayrollCoreExceptionCode(Class<?> clazz, String msgId) {
        this.clazz = clazz;
        this.msgId = msgId;
    }

}
