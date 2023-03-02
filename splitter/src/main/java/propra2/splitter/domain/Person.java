package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Comparator;
import java.util.List;

public class Person{

    String name;
    List<Expense> expenses;
    List<Debt> debts;

    public Person(String name, List<Expense> expenses, List<Debt> debts) {
        this.name = name;
        this.expenses = expenses;
        this.debts = debts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }

    //    @Override
//    public int compare(Person o1, Person o2) {
//        if(o1.totalExpense.subtract(o2.totalExpense).isLessThan(Money.of(0, "EUR"))){
//            return -1;
//        }
//        else if(o1.totalExpense.subtract(o2.totalExpense).isEqualTo(Money.of(0, "EUR"))){
//            return 0;
//        } else {
//            return 1;
//        }
//    }
}


