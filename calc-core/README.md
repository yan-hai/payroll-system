# Payroll Calculation Core

## What `Core` Can Do
1. Provide a unified definition of the data(Item) and rules(Formula) used during the calculation
1. Calculate the result based on the given data set and rules 
1. Offers a single interface for caller to execute the normal or retroactive calculation
1. Handle the changes of formula within a execution period
1. handle the changes of item value within a execution period
1. Prorate item value based on related calendar info
1. Rounding item value

## What `Core` Can Not Do
1. Fetch data set for the calculation
1. Manage calendar information, like, attendence, leave, shift.
1. Handle interface for external data
1. Register Formula/Item/Proration/Task
1. Manage HR related information
1. Generate Report
1. Workflow control
1. Task scheduler

## Architecture

> https://drive.google.com/file/d/1tM96jXBHbzjGYEemqEuiHBpjgKdTb2tw/view?usp=sharing

![Architecture](doc/pics/architecture.png)