package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.item.ItemFactory;
import lombok.Data;

import java.time.Period;
import java.util.Set;

/**
 * @author Ryan
 */
@Data
public abstract class Formula implements Comparable<Formula>{
    /**
     * the id of item whose value will be evaluated from this formula
     */
    protected String targetItemId;
    /**
     * Unique formula id
     */
    protected String formulaId;
    /**
     * Name of formula
     */
    protected String formulaName;
    /**
     * Valid Period
     */
    protected Period validPeriod;
    /**
     * The priority which decide the order to evaluate the formula
     * <p>
     * A smaller number means A higher priority and evaluated earlier
     */
    protected int priority = 1000;

    /**
     * Get the instance of various types of items
     */
    protected ItemFactory itemFactory;

    /**
     * Get ids of required items in order to evaluate the formula
     *
     * @return
     */
    public abstract Set<String> getRequiredItems();

    @Override
    public int compareTo(Formula o) {
        return this.priority - o.priority;
    }
}
