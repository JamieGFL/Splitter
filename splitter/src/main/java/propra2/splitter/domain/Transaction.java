package propra2.splitter.domain;

import org.javamoney.moneta.Money;

public class Transaction {
    Person personA;
    Person personB;
    Money amount;

    public Transaction(Person personA, Person personB) {
        this.personA = personA;
        this.personB = personB;
    }

    private Money calculatePersonADebt(){
        Money amountA = Money.of(0, "EUR");
        for(Expense expense : personB.expenses()){
            if(expense.getPersons().contains(personA)){
                amountA = amountA.add(expense.getAverageCost());
            }
        }
        return amountA;
    }

    private Money calculatePersonBDebt(){
        Money amountB = Money.of(0, "EUR");
        for(Expense expense : personA.expenses()){
            if(expense.getPersons().contains(personB)){
                amountB = amountB.add(expense.getAverageCost());
            }
        }
        return amountB;
    }
    private Money calculateAmount(){
        return calculatePersonADebt().subtract(calculatePersonBDebt());
    }

    public String getRequiredTransaction(){
        amount = calculateAmount();
        if(amount.isGreaterThan(Money.of(0, "EUR"))){
            return personA.name() +" has to pay " + personB.name() + " an amount of " + amount;
        }
        if(amount.isLessThan(Money.of(0, "EUR"))){
            return personB.name() +" has to pay " + personA.name() + " an amount of " + amount.abs();
        }
        else if(amount.isEqualTo(Money.of(0, "EUR"))){
            return personA.name() +" and "+ personB.name() +" don't owe anything to each other";
        }
        return null;
    }
}
