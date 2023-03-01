package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Comparator;
import java.util.List;

public record Person(String name, List<Expense> expenses, Money totalExpense) implements Comparator<Person> {


    @Override
    public int compare(Person o1, Person o2) {
        if(o1.totalExpense.subtract(o2.totalExpense).isLessThan(Money.of(0, "EUR"))){
            return -1;
        }
        else if(o1.totalExpense.subtract(o2.totalExpense).isEqualTo(Money.of(0, "EUR"))){
            return 0;
        } else {
            return 1;
        }
    }
}


