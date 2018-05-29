package com.nobodyhub.payroll.core.util;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONVERTER_NOT_FOUND;

/**
 * @author yan_h
 * @since 2018-05-11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValueConverter {
    public static <T> T convertFromString(String value, Class<T> cls) throws PayrollCoreException {
        Objects.requireNonNull(value);
        Object rst;
        if (cls == String.class) {
            rst = value;
        } else if (cls == BigDecimal.class) {
            rst = new BigDecimal(value);
        } else if (cls == Boolean.class) {
            rst = Boolean.valueOf(value);
        } else if (cls == LocalDate.class) {
            rst = DateFormatUtils.parseDate(value);
        } else if (cls == LocalTime.class) {
            rst = DateFormatUtils.parseTime(value);
        } else if (cls == LocalDateTime.class) {
            rst = DateFormatUtils.parseDateTime(value);
        } else {
            throw new PayrollCoreException(CONVERTER_NOT_FOUND)
                    .addValue("value", value)
                    .addValue("cls", cls);
        }
        return cls.cast(rst);
    }

    public static String convertToString(Object object) throws PayrollCoreException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).toPlainString();
        } else if (object instanceof Boolean) {
            return ((Boolean) object).toString();
        } else if (object instanceof LocalDate) {
            return DateFormatUtils.convertDate((LocalDate) object);
        } else if (object instanceof LocalTime) {
            return DateFormatUtils.convertTime((LocalTime) object);
        } else if (object instanceof LocalDateTime) {
            return DateFormatUtils.convertDateTime((LocalDateTime) object);
        } else {
            throw new PayrollCoreException(CONVERTER_NOT_FOUND)
                    .addValue("object", object);
        }
    }
}
