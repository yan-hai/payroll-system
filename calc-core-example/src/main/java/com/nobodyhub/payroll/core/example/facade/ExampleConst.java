package com.nobodyhub.payroll.core.example.facade;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author yan_h
 * @since 2018/6/2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExampleConst {
    public static final String TASK_ID = "task_example";

    /*
     * payment items
     */

    /**
     * basic salary stated in the contract
     */
    public static final String PAY_BASIC_SALARY = "pay_basic_salary";

    /**
     * deduction caused by absense
     * = daily salary * # of unpaid leave
     */
    public static final String PAY_UNPAID_LEAVE = "pay_unpaid_leave";

    /**
     * Daily salary
     * = basic salary / working days
     * used to calculate items based on daily basic salary
     */
    public static final String PAY_DAILY_SALARY = "pay_daily_salary";

    /**
     * Overtime Allowance
     * = number of OT * daily salary
     */
    public static final String PAY_OVERTIME_ALLOWANCE = "pay_OT_allowance";

    /**
     * Total payment
     * = basic salary + OT allowance -
     */
    public static final String PAY_TOTAL_SALARY = "pay_total_salary";

    /*
     * calendar items
     */

    /**
     * working days
     * (paid leaves are sort of working days)
     */
    public static final String CAL_WORK_DAY = "cal_workday";

    /**
     * Unpaid leaves
     */
    public static final String CAL_UNPAID_LEAVE = "cal_unpaid_leave";

    /**
     * overtime,
     * dayly based, 1, 1.5, 1.25, and etc.
     */
    public static final String CAL_OVERTIME = "cal_overtime";

    /*
     *proration
     */

    /**
     * Prorated based on work day
     */
    public static final String PRO_WORKDAY = "pro_workday";

    /*
     * normal formula
     */
    /**
     * Formula for daily salary
     */
    public static final String FOR_DAILY_SALARY = "for_daily_salary";
    /**
     * Formula for unpaid leave
     */
    public static final String FOR_UNPAID_LEAVE = "for_daily_salary";
    /**
     * Formula for overtime allowances
     */
    public static final String FOR_OVERTIME_ALLOWANCE = "for_daily_salary";
    /**
     * Formula for total salary
     */
    public static final String FOR_TOTAL_SALARY = "for_daily_salary";

}
