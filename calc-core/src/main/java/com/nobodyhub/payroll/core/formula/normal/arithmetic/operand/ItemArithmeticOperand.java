package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.nobodyhub.payroll.core.formula.common.operand.ItemOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.ArithmeticOperand;
import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;

/**
 * The operand that contains a {@link Item}
 *
 * @author yan_h
 * @since 2018-05-10
 */
public class ItemArithmeticOperand extends ItemOperand<BigDecimal> implements ArithmeticOperand {

    private ItemArithmeticOperand(String itemId) {
        super(itemId, BigDecimal.class);
    }

    /**
     * contruct operand from a {@link String} itemId
     *
     * @param itemId
     * @return
     */
    public static ItemArithmeticOperand of(String itemId) {
        return new ItemArithmeticOperand(itemId);
    }
}
