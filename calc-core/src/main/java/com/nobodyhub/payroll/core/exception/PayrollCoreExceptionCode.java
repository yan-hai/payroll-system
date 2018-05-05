package com.nobodyhub.payroll.core.exception;

import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.item.ItemContext;
import lombok.Getter;

/**
 * Error Code
 *
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
    /**
     * Can not find required item in the context
     */
    CONTEXT_NOT_FOUND(ItemContext.class, "");

    /**
     * The class which throws the exception
     */
    private Class<?> clazz;
    /**
     * The msg id of the error message
     */
    private String msgId;

    PayrollCoreExceptionCode(Class<?> clazz, String msgId) {
        this.clazz = clazz;
        this.msgId = msgId;
    }

}
