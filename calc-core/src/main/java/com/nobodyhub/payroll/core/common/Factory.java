package com.nobodyhub.payroll.core.common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Factory for caching the shared objects across execution
 *
 * @author yan_h
 * @since 2018-05-11
 */
public abstract class Factory<T extends Identifiable> {
    protected Map<String, T> contents = Maps.newHashMap();

    /**
     * get content from cache by id
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return contents.get(id);
    }

    /**
     * add content to cache for id
     *
     * @param content
     */
    public void add(T content) {
        this.contents.put(content.getId(), content);
    }

    /**
     * initialize the contents
     */
    public abstract void initContents();
}
