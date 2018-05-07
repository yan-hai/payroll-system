package com.nobodyhub.payroll.core.item;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.attendance.DayCountItem;
import com.nobodyhub.payroll.core.item.attendance.FrequencyCountItem;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.common.ItemBuilder;
import com.nobodyhub.payroll.core.item.hr.HrDateItem;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.item.payment.PaymentType;

import java.util.Map;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.FACTORY_NOT_FOUND;

/**
 * The factory class that is responsible for generating
 *
 * @author Ryan
 */
public abstract class ItemFactory {
    protected Map<String, ItemBuilder> itemBuilders = Maps.newHashMap();

    public Item getItem(String itemId) throws PayrollCoreException {
        ItemBuilder builder = itemBuilders.get(itemId);
        if (builder == null) {
            throw new PayrollCoreException(FACTORY_NOT_FOUND)
                    .addValue("itemId", itemId);
        }
        return (Item) builder.build();
    }

    /**
     * initialize specific items that pre-defined for each payroll extension
     */
    protected abstract void initSpecific();

    /**
     * Load user-defined items from DB to initialize
     */
    protected void initCommon() {
        //TODO: load common items from database
        // payments
        itemBuilders.put("FIXED_SALARY",
                new PaymentItem("FIXED_SALARY", PaymentType.ALLOWANCE));
        itemBuilders.put("UNFIXED_SALARY",
                new PaymentItem("UNFIXED_SALARY", PaymentType.ALLOWANCE));
        itemBuilders.put("OVERTIME_ALLOWANCE",
                new PaymentItem("OVERTIME_ALLOWANCE", PaymentType.ALLOWANCE));
        itemBuilders.put("UNPAID_LEAVE",
                new PaymentItem("UNPAID_LEAVE", PaymentType.DEDUCTION));
        //attendance
        itemBuilders.put("UNPAID_TIMES",
                new FrequencyCountItem("UNPAID_TIMES"));
        itemBuilders.put("CALENDAR_DAYS",
                new DayCountItem("CALENDAR_DAYS"));
        itemBuilders.put("WORKING_DAYS",
                new DayCountItem("WORKING_DAYS"));
        //hr
        itemBuilders.put("JOIN_DATE",
                new HrDateItem("JOIN_DATE"));
    }


    public void init() {
        initCommon();
        initSpecific();
    }
}
