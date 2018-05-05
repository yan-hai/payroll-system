package com.nobodyhub.payroll.core.item;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.abstr.Item;
import com.nobodyhub.payroll.core.item.abstr.ItemBuilder;

import java.util.Map;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.FACTORY_NOT_FOUND;

/**
 * The factory class that is responsible for generating
 *
 * @author Ryan
 */
public abstract class ItemFactory {
    private Map<String, ItemBuilder> itemBuilders = Maps.newHashMap();

    public Item getItem(String itemId) throws PayrollCoreException {
        ItemBuilder builder = itemBuilders.get(itemId);
        if (builder == null) {
            throw new PayrollCoreException(FACTORY_NOT_FOUND)
                    .addValue("itemId", itemId);
        }
        return (Item) builder.build();
    }

    public abstract void initItemClassMap();
}
