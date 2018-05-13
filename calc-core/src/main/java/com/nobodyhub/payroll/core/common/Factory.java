package com.nobodyhub.payroll.core.common;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

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
        T content = contents.get(id);
        Objects.requireNonNull(content, String.format("Content is not found for %s : [%s].", this.getClass(), id));
        return content;
    }

    /**
     * add content to cache for id
     *
     * @param content
     */
    public void add(T content) {
        Objects.requireNonNull(content, "Content to be added should not be null!");
        this.contents.put(content.getId(), content);
    }

    /**
     * initialize the contents
     */
    public abstract void initContents();
}
