package propra2.splitter.domain;

import org.javamoney.moneta.Money;

public class Debt {

    Person payer;
    Person payee;
    Money amount;

    public Debt(Person payer, Person payee, Money amount) {
        this.payer = payer;
        this.payee = payee;
        this.amount = calculateDebt();
    }

    public Money calculateDebt(){
        Money payerDebt = Money.of(0, "EUR");
        for(Expense expense:payee.getExpenses()){
            if(expense.getPersons().contains(payer)){
                payerDebt = payerDebt.add(expense.getAverageCost());
            }
        }
        return payerDebt;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public Person getPayee() {
        return payee;
    }

    public void setPayee(Person payee) {
        this.payee = payee;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
