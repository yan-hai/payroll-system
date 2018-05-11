package com.nobodyhub.payroll.core.exception;

import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.util.ValueConverter;
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
     * Unknown value type to handle
     */
    ITEM_VALUE_UNKNOWN(Item.class, ""),
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
    FACTORY_NOT_FOUND(ItemBuilderFactory.class, ""),
    /**
     * Item class found is not compatible with required
     */
    FACTORY_INCOMPATIBLE(ItemBuilderFactory.class, ""),
    /**
     * Item does not have the required constructor(with one String parameter, as itemId)
     */
    FACTORY_NO_REQUIRED_CONSTRUCTOR(ItemBuilderFactory.class, ""),
    /**
     * Retroactive formula fail to apply
     */
    RETRO_FORMULA_FAIL(RetroFormula.class, ""),
    /**
     * Format of History values is wrong
     */
    HISTORY_DATA_MALFORMED(HistoryData.class, ""),
    /**
     * Invalid History Data
     */
    HISTORY_DATA_INVALID(HistoryData.class, ""),
    /**
     * Invalid period
     */
    PERIOD_INVALID(Period.class, ""),

    /**
     * No converted found
     */
    CONVERTER_NOT_FOUND(ValueConverter.class, ""),


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
