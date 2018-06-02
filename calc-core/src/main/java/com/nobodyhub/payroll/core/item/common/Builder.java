package com.nobodyhub.payroll.core.item.common;

import com.nobodyhub.payroll.core.common.Identifiable;

/**
 * Interface for a Builder
 *
 * @author Ryan
 */
public interface Builder<IT> extends Identifiable {
    /**
     * create a new instance
     *
     * @return new instance
     */
    IT build();
}
