package com.nobodyhub.payroll.core.exception;

import com.nobodyhub.payroll.core.common.ItemComparator;
import lombok.Getter;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public enum PayrollCoreExceptionCode {
    /**
     * Unimplemented handler for {@link ItemComparator}
     */
    COMPARATOR_UNIMPLEMENTED(ItemComparator.class, "");

    private Class<?> clazz;
    private String msgId;

    PayrollCoreExceptionCode(Class<?> clazz, String msgId) {
        this.clazz = clazz;
        this.msgId = msgId;
    }

}
