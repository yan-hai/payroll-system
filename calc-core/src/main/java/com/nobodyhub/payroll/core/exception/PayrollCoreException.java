package com.nobodyhub.payroll.core.exception;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Checked Exception for any error within calc-core
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public final class PayrollCoreException extends Exception {
    private final PayrollCoreExceptionCode code;
    protected final Map<String, String> values;

    public PayrollCoreException(PayrollCoreExceptionCode code) {
        super();
        this.code = code;
        this.values = Maps.newHashMap();
    }

    public PayrollCoreException addValue(String key, String value) {
        this.values.put(key, value);
        return this;
    }

    public PayrollCoreException addValue(String key, LocalDateTime value) {
        return addValue(key, value.toString());
    }

    public PayrollCoreException addValue(String key, Iterable<String> value) {
        return addValue(key, Joiner.on(", ").join(value));
    }

    public PayrollCoreException addValue(String key, Object value) {
        return addValue(key, value.toString());
    }

    public PayrollCoreException addMessage(String message) {
        return addValue("Error Message", message);
    }

    public PayrollCoreException addClass(Class<?> clazz) {
        return addValue("Class", clazz.getSimpleName());
    }

    public PayrollCoreException addMethod(String methodName) {
        return addValue("Method", methodName);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("** Exception Code: [" + code + "]\n");
        sb.append("** Values:\n");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            sb.append("\t[" + entry.getKey() + "]=[" + entry.getValue() + "]\n");
        }
        return sb.toString();
    }
}
