package com.nobodyhub.payroll.core.formula.retro;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.ExecutionContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.RETRO_FORMULA_FAIL;

/**
 * @author Ryan
 */
public class RetroactiveFormula extends Formula {
    protected Set<String> relatedItemIds = Sets.newHashSet();

    public PaymentItem evaluate(List<ExecutionContext> newHistoryValues, List<Map<String, String>> valueMap) throws PayrollCoreException {
        if (newHistoryValues.size() != valueMap.size()) {
            throw new PayrollCoreException(RETRO_FORMULA_FAIL)
                    .addValue("newHistoryValues", newHistoryValues)
                    .addValue("valueMap", valueMap);
        }
        Map<String, BigDecimal> diffValues = Maps.newHashMap();
        for (int idx = 0; idx < newHistoryValues.size(); idx++) {
            ExecutionContext newValues = newHistoryValues.get(idx);
            Map<String, String> oldValues = valueMap.get(idx);
            for (String itemId : relatedItemIds) {
                Item item = newValues.get(itemId);
                if (item instanceof PaymentItem) {
                    PaymentItem paymentItem = (PaymentItem) item;
                    BigDecimal exist = diffValues.get(itemId) == null ?
                            BigDecimal.ZERO : diffValues.get(itemId);
                    exist = exist.add(paymentItem.getValue()
                            .subtract(new BigDecimal(oldValues.get(itemId))));
                    diffValues.put(itemId, exist);
                } else {
                    //TODO: log skip non-payment item
                }
            }
        }
        BigDecimal rst = BigDecimal.ZERO;
        for (BigDecimal val : diffValues.values()) {
            rst = rst.add(val);
        }
        return createPaymentItem(rst);
    }

    @Override
    public Set<String> getRequiredItems() {
        return new HashSet<>(relatedItemIds);
    }
}
