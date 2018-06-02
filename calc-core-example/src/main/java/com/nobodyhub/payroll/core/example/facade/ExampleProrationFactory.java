package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.proration.impl.CalendarProration;

import static com.nobodyhub.payroll.core.example.facade.ExampleConst.CAL_WORK_DAY;
import static com.nobodyhub.payroll.core.example.facade.ExampleConst.PRO_WORKDAY;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleProrationFactory extends ProrationFactory {
    public ExampleProrationFactory() {
        initContents();
    }
    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new CalendarProration(PRO_WORKDAY, CAL_WORK_DAY));
    }
}
