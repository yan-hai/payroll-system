package com.nobodyhub.payroll.core.item;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.util.Map;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.*;

/**
 * The factory class that is responsible for generating
 *
 * @author Ryan
 */
public abstract class ItemFactory {
    private Map<String, Class<?>> itemClassMap = Maps.newHashMap();

    public Item getItem(String itemId, Class valueCls) throws PayrollCoreException {
        Class<?> itemCls = itemClassMap.get(itemId);
        if (itemCls == null) {
            throw new PayrollCoreException(FACTORY_NOT_FOUND)
                    .addValue("itemId", itemId)
                    .addValue("cls", valueCls);
        }
        try {
            Item item = (Item) itemCls.getDeclaredConstructor(String.class).newInstance(itemId);
            if (item.getValueCls() == valueCls) {
                throw new PayrollCoreException(FACTORY_INCOMPATIBLE)
                        .addValue("itemId", itemId)
                        .addValue("cls", valueCls);
            }
            return item;
        } catch (Exception e) {
            throw new PayrollCoreException(FACTORY_NO_REQUIRED_CONSTRUCTOR)
                    .addValue("itemId", itemId)
                    .addValue("cls", valueCls)
                    .addMessage(e.getMessage());
        }
    }

    public abstract void initItemClassMap();
}
