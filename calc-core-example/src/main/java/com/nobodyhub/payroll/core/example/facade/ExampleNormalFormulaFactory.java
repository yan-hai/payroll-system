package com.nobodyhub.payroll.core.example.facade;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.common.Function;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.formula.normal.aggregation.AggregationFormula;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.ArithmeticFormula;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.FormulaExpression;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ItemArithmeticOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ValueArithmeticOperand;
import com.nobodyhub.payroll.core.formula.normal.map.FormulaCase;
import com.nobodyhub.payroll.core.formula.normal.map.FormulaCaseSet;
import com.nobodyhub.payroll.core.formula.normal.map.FormulaCondition;
import com.nobodyhub.payroll.core.formula.normal.map.MapFormula;
import com.nobodyhub.payroll.core.formula.normal.map.operand.ValueConditionOperand;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.hr.HrOptionItem;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.nobodyhub.payroll.core.example.facade.ExampleConst.*;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleNormalFormulaFactory extends NormalFormulaFactory {
    public ExampleNormalFormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        super(itemBuilderFactory);
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        basicSalary();
        dailySalary();
        unpaidLeave();
        overtimeAllowance();
        totalSalary();
    }

    public void basicSalary() {
        MapFormula basicSalary = new MapFormula(
                FOR_BASIC_SALARY,
                PAY_BASIC_SALARY,
                itemBuilderFactory);

        FormulaCaseSet formulaCaseSet = new FormulaCaseSet(BigDecimal.ZERO);

        FormulaCase case1 = new FormulaCase(0, new BigDecimal("3000"));
        case1.addCondition(new FormulaCondition<>(
                new HrOptionItem(HR_POSITION),
                Comparator.EQ,
                ValueConditionOperand.of("lvl1"),
                null
        ));
        formulaCaseSet.addCase(case1);

        FormulaCase case2 = new FormulaCase(1, new BigDecimal("6000"));
        case2.addCondition(new FormulaCondition<>(
                new HrOptionItem(HR_POSITION),
                Comparator.EQ,
                ValueConditionOperand.of("lvl2"),
                null
        ));
        formulaCaseSet.addCase(case2);

        FormulaCase case3 = new FormulaCase(2, new BigDecimal("9000"));
        case3.addCondition(new FormulaCondition<>(
                new HrOptionItem(HR_POSITION),
                Comparator.EQ,
                ValueConditionOperand.of("lvl3"),
                null
        ));
        formulaCaseSet.addCase(case3);

        basicSalary.addContent(LocalDate.of(2018, 5, 1), formulaCaseSet);
        add(basicSalary);
    }

    public void dailySalary() {
        ArithmeticFormula dailySalary = new ArithmeticFormula(
                FOR_DAILY_SALARY,
                PAY_DAILY_SALARY,
                itemBuilderFactory);

        FormulaExpression divisor = new FormulaExpression(
                null,
                ItemArithmeticOperand.of(CAL_WORK_DAY, Function.COUNT),
                null
        );

        FormulaExpression expression = new FormulaExpression(
                Operator.DIV,
                ItemArithmeticOperand.of(PAY_BASIC_SALARY),
                divisor
        );

        dailySalary.addContent(LocalDate.of(2018, 5, 1),
                expression);
        add(dailySalary);
    }

    public void unpaidLeave() {
        ArithmeticFormula unpaidLeave = new ArithmeticFormula(
                FOR_UNPAID_LEAVE,
                PAY_UNPAID_LEAVE,
                itemBuilderFactory);
        FormulaExpression mul2 = new FormulaExpression(
                null,
                ItemArithmeticOperand.of(CAL_UNPAID_LEAVE, Function.COUNT),
                null
        );

        FormulaExpression mul1 = new FormulaExpression(
                Operator.MUL,
                ItemArithmeticOperand.of(PAY_DAILY_SALARY),
                mul2
        );

        FormulaExpression expression = new FormulaExpression(
                Operator.MUL,
                ValueArithmeticOperand.of("-1"),
                mul1
        );

        unpaidLeave.addContent(LocalDate.of(2018, 5, 1),
                expression);
        add(unpaidLeave);
    }

    public void overtimeAllowance() {
        ArithmeticFormula overtimeAllowance = new ArithmeticFormula(
                FOR_OVERTIME_ALLOWANCE,
                PAY_OVERTIME_ALLOWANCE,
                itemBuilderFactory);

        FormulaExpression multiplier = new FormulaExpression(
                null,
                ItemArithmeticOperand.of(CAL_OVERTIME, Function.SUM),
                null
        );

        FormulaExpression expression = new FormulaExpression(
                Operator.MUL,
                ItemArithmeticOperand.of(PAY_DAILY_SALARY),
                multiplier
        );

        overtimeAllowance.addContent(LocalDate.of(2018, 5, 1),
                expression);
        add(overtimeAllowance);
    }

    public void totalSalary() {
        AggregationFormula totalSalary = new AggregationFormula(
                FOR_TOTAL_SALARY,
                PAY_TOTAL_SALARY,
                itemBuilderFactory);

        totalSalary.addContent(LocalDate.of(2018, 5, 1),
                Sets.newHashSet(
                        PAY_BASIC_SALARY,
                        PAY_UNPAID_LEAVE,
                        PAY_OVERTIME_ALLOWANCE
                ));
        add(totalSalary);
    }
}
