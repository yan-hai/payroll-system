package com.nobodyhub.payroll.core.item;

import com.nobodyhub.payroll.core.common.Factory;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Builder;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.proration.ProrationFactory;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.FACTORY_INCOMPATIBLE;
import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.FACTORY_NOT_FOUND;

/**
 * The factory class that is responsible for building items instances
 *
 * @author Ryan
 */
public abstract class ItemBuilderFactory extends Factory<Builder> {
    protected final ProrationFactory prorationFactory;

    protected ItemBuilderFactory(ProrationFactory prorationFactory) {
        this.prorationFactory = prorationFactory;
    }

    /**
     * Build item instance of given itemId
     *
     * @param itemId
     * @return
     * @throws PayrollCoreException
     */
    public Item getItem(String itemId) throws PayrollCoreException {
        Builder builder = get(itemId);
        if (builder == null) {
            throw new PayrollCoreException(FACTORY_NOT_FOUND)
                    .addValue("itemId", itemId);
        }
        return (Item) builder.build();
    }

    /**
     * get the item of specified class
     *
     * @param itemId
     * @param itemCls
     * @param <T>
     * @return
     * @throws PayrollCoreException
     */
    public <T> T getItem(String itemId, Class<T> itemCls) throws PayrollCoreException {
        Item item = getItem(itemId);
        if (itemCls.isAssignableFrom(item.getClass())) {
            return itemCls.cast(item);
        }
        throw new PayrollCoreException(FACTORY_INCOMPATIBLE)
                .addValue("itemId", itemId)
                .addValue("itemCls", itemCls);
    }
}
