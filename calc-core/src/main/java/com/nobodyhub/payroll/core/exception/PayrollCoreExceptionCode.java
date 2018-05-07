package com.nobodyhub.payroll.core.exception;

import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentType;
import com.nobodyhub.payroll.core.task.ExecutionContext;
import lombok.Getter;

/**
 * Error Code for different type of error
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
    CONTEXT_NOT_FOUND(ExecutionContext.class, ""),
    /**
     * Item found in the context is incompatible with required
     */
    CONTEXT_INCOMPATIBLE(ExecutionContext.class, ""),
    /**
     * Can not find Item class
     */
    FACTORY_NOT_FOUND(ItemFactory.class, ""),
    /**
     * Item class found is not compatible with required
     */
    FACTORY_INCOMPATIBLE(ItemFactory.class, ""),
    /**
     * Item does not have the required constructor(with one String parameter, as itemId)
     */
    FACTORY_NO_REQUIRED_CONSTRUCTOR(ItemFactory.class, ""),
    /**
     * Unimplemented handler for {@link PaymentType}
     */
    PAYMENTTYPE_UNIMPLEMENTED(PaymentType.class, ""),
    /**
     * Retroactive formula fail to apply
     */
    RETRO_FORMULA_FAIL(RetroFormula.class, ""),

    /**
     * TODO: add more codes
     */
    ;

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
