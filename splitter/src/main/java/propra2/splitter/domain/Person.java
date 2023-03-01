package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;

public record Person(String name, List<Expense> expenses, Money totalExpense) {

}


