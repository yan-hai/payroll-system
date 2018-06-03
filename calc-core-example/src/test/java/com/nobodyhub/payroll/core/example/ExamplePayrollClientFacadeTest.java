package com.nobodyhub.payroll.core.example;

import com.google.common.collect.Lists;
import com.nobodyhub.payroll.core.PayrollCoreClientFacade;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.client.PayrollCoreClient;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static com.nobodyhub.payroll.core.example.facade.ExampleConst.*;
import static junit.framework.TestCase.assertEquals;

/**
 * @author yan_h
 * @since 2018/6/2
 */
public class ExamplePayrollClientFacadeTest extends ExamplePayrollServerFacadeTest {
    private PayrollCoreClientFacade clientFacade;

    private PayrollCoreClient client;

    @Before
    public void setup() throws IOException {
        super.setup();
        clientFacade = new ExamplePayrollClientFacade();
        client = clientFacade.client();
    }

    @Test
    public void test() throws PayrollCoreException, InterruptedException {
        PayrollCoreProtocol.PeriodValue.Builder currentValueBuilder =
                PayrollCoreProtocol.PeriodValue.newBuilder()
                        .setStartDate("20180501")
                        .setEndDate("20180531")
                        .setBaseDate("20180525");

        addPaymentItem(currentValueBuilder);
        addCalendarItem(currentValueBuilder);

        PayrollCoreProtocol.Request request
                = PayrollCoreProtocol.Request.newBuilder()
                .setTaskId(TASK_ID)
                .setDataId("Employee ID1")
                .setCurrentValue(currentValueBuilder.build())
                .build();

        Map<String, Map<String, String>> results = client.calculate(Lists.newArrayList(
                request
        ));
        assertEquals(1, results.size());
        assertEquals("6666.667", results.get("Employee ID1").get(PAY_BASIC_SALARY));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        client.shutdown();
    }

    private void addPaymentItem(
            PayrollCoreProtocol.PeriodValue.Builder currentValueBuilder) {
        PayrollCoreProtocol.ItemValue itemValue =
                PayrollCoreProtocol.ItemValue.newBuilder()
                        .setItemId(HR_POSITION)
                        .putValues("20180501", "lvl1")
                        .putValues("20180511", "lvl2")
                        .putValues("20180521", "lvl3")
                        .build();
        currentValueBuilder.addItems(itemValue);
    }


    private void addCalendarItem(
            PayrollCoreProtocol.PeriodValue.Builder currentValueBuilder) {
        PayrollCoreProtocol.ItemValue.Builder itemValueBuilder =
                PayrollCoreProtocol.ItemValue.newBuilder()
                        .setItemId(CAL_WORK_DAY);
        //20180501~20180510, 4 out of 10 are working days
        itemValueBuilder.putValues("20180501", "1");// working day
        itemValueBuilder.putValues("20180502", "1");// working day
        itemValueBuilder.putValues("20180503", "1");// working day
        itemValueBuilder.putValues("20180504", "1");// working day
        itemValueBuilder.putValues("20180505", "0");
        itemValueBuilder.putValues("20180506", "0");
        itemValueBuilder.putValues("20180507", "0");
        itemValueBuilder.putValues("20180508", "0");
        itemValueBuilder.putValues("20180509", "0");
        itemValueBuilder.putValues("20180510", "0");
        //20180511~20180531, 14 out of 21 are working days
        itemValueBuilder.putValues("20180511", "1");// working day
        itemValueBuilder.putValues("20180512", "1");// working day
        itemValueBuilder.putValues("20180513", "1");// working day
        itemValueBuilder.putValues("20180514", "1");// working day
        itemValueBuilder.putValues("20180515", "1");// working day
        itemValueBuilder.putValues("20180516", "0");
        itemValueBuilder.putValues("20180517", "0");
        itemValueBuilder.putValues("20180518", "0");
        itemValueBuilder.putValues("20180519", "0");
        itemValueBuilder.putValues("20180520", "1");// working day
        itemValueBuilder.putValues("20180521", "1");// working day
        itemValueBuilder.putValues("20180522", "1");// working day
        itemValueBuilder.putValues("20180523", "1");// working day
        itemValueBuilder.putValues("20180524", "1");// working day
        itemValueBuilder.putValues("20180525", "0");
        itemValueBuilder.putValues("20180526", "0");
        itemValueBuilder.putValues("20180527", "1");// working day
        itemValueBuilder.putValues("20180528", "1");// working day
        itemValueBuilder.putValues("20180529", "1");// working day
        itemValueBuilder.putValues("20180530", "1");// working day
        itemValueBuilder.putValues("20180531", "0");

        currentValueBuilder.addItems(itemValueBuilder.build());
    }
}