package com.nobodyhub.payroll.core.proration;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.proration.abstr.Proration;

import java.util.Map;
import java.util.Objects;

/**
 * Contains all available Proration rules
 *
 * @author yan_h
 * @since 2018-05-10.
 */
public abstract class ProrationContainer {

    protected Map<String, Proration> prorations = Maps.newHashMap();

    /**
     * get Proration from container
     *
     * @param id
     * @return
     */
    public Proration get(String id) {
        Proration proration = prorations.get(id);
        Objects.requireNonNull(proration, String.format("Proration is not found for id : [%s].", id));
        return proration;
    }

    /**
     * add proration to container
     *
     * @param proration
     */
    public void add(Proration proration) {
        Objects.requireNonNull(proration, "Proration to be added should not be null!");
        this.prorations.put(proration.getProrationId(), proration);
    }
}
