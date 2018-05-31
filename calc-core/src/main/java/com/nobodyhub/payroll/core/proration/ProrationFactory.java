package com.nobodyhub.payroll.core.proration;

import com.nobodyhub.payroll.core.common.Factory;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.proration.abstr.Proration;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Contains all available Proration rules
 *
 * @author yan_h
 * @since 2018-05-10.
 */
public abstract class ProrationFactory extends Factory<Proration> {
    /**
     * prorate values based on the given {@code prorationId}
     * if {@code prorationId} is null, or not proration relates to that id,
     * will return aggregated value
     *
     * @param prorationId
     * @param values
     * @return
     */
    public BigDecimal prorate(String prorationId,
                              ExecutionContext context,
                              Map<LocalDate, BigDecimal> values) throws PayrollCoreException {
        Proration proration = get(prorationId);
        if (prorationId == null || proration == null) {
            return values.values().stream().reduce(BigDecimal.ZERO,
                    BigDecimal::add);
        }
        return proration.getFinalValue(context, values);
    }
}
