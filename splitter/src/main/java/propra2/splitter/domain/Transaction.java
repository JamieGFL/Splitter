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
        for(Expense expense : personB.getExpenses()){
            if(expense.getPersons().contains(personA)){
                amountA = amountA.add(expense.getAverageCost());
            }
        }
        return amountA;
    }

    private Money calculatePersonBDebt(){
        Money amountB = Money.of(0, "EUR");
        for(Expense expense : personA.getExpenses()){
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
            return personA.getName() +" has to pay " + personB.getName() + " an amount of " + amount;
        }
        if(amount.isLessThan(Money.of(0, "EUR"))){
            return personB.getName() +" has to pay " + personA.getName() + " an amount of " + amount.abs();
        }
        else if(amount.isEqualTo(Money.of(0, "EUR"))){
            return personA.getName() +" and "+ personB.getName() +" don't owe anything to each other";
        }
        return null;
    }

    public Person getPersonA() {
        return personA;
    }

    public void setPersonA(Person personA) {
        this.personA = personA;
    }

    public Person getPersonB() {
        return personB;
    }

    public void setPersonB(Person personB) {
        this.personB = personB;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
