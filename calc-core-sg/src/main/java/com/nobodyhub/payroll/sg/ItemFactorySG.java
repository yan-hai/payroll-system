package com.nobodyhub.payroll.sg;

import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.attendance.DayCountItem;
import com.nobodyhub.payroll.core.item.hr.HrBooleanItem;
import com.nobodyhub.payroll.core.item.hr.HrDateItem;
import com.nobodyhub.payroll.core.item.hr.HrNumberItem;
import com.nobodyhub.payroll.core.item.hr.HrStringItem;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.item.payment.PaymentType;

/**
 * @author Ryan
 */
public class ItemFactorySG extends ItemFactory {
    protected static final String SPR_START_DATE = "SPR_START_DATE";
    protected static final String BIRTHDAY = "BIRTHDAY";
    protected static final String POSITION = "POSITION";
    protected static final String CITIZENSHIP = "CITIZENSHIP";
    protected static final String CDAC = "CDAC";
    protected static final String CDAC_AMOUNT = "CDAC_AMOUNT";
    protected static final String SPR_DAYS = "SPR_DAYS";
    protected static final String SALARY_TO_CPF = "SALARY_TO_CPF";
    protected static final String CPF_CONTRIBUTION = "CPF_CONTRIBUTION";


    @Override
    protected void initSpecific() {
        //TODO: use maven-plugin to generate
        //payment
        itemBuilders.put(SALARY_TO_CPF, new PaymentItem(SALARY_TO_CPF, PaymentType.ALLOWANCE));
        itemBuilders.put(CPF_CONTRIBUTION, new PaymentItem(CPF_CONTRIBUTION, PaymentType.DEDUCTION));

        itemBuilders.put(CDAC, new HrBooleanItem(CDAC));
        itemBuilders.put(CDAC_AMOUNT, new HrNumberItem(CDAC_AMOUNT));
        itemBuilders.put(SPR_START_DATE, new HrDateItem(SPR_START_DATE));
        itemBuilders.put(BIRTHDAY, new HrDateItem(BIRTHDAY));
        itemBuilders.put(POSITION, new HrStringItem(POSITION));
        itemBuilders.put(CITIZENSHIP, new HrDateItem(CITIZENSHIP));

        itemBuilders.put(SPR_DAYS,
                new DayCountItem(SPR_DAYS));
    }
}
