package com.nobodyhub.payroll.core.common;

/**
 * Mark objects that could be identified by a unique id
 *
 * @author yan_h
 * @since 2018-05-11
 */
public interface Identifiable {
    /**
     * get the unique identifier
     *
     * @return unique id
     */
    String getId();
}
