package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.proration.impl.CalendarProration;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleProrationFactory extends ProrationFactory {
    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new CalendarProration("workday_prorated", "workday_item"));
        add(new CalendarProration("calendarday_prorated", "calendarday_item"));
        add(new CalendarProration("calendarday_prorated", "overtime_item"));
    }
}
