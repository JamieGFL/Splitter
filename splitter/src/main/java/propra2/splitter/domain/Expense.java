package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;

public class Expense {
    Activity activity;
    Person payer;
    List<Person> persons;
    Money cost;

    public Expense(Activity activity, Person payer, List<Person> persons, Money cost) {
        this.activity = activity;
        this.payer = payer;
        this.persons = persons;
        this.cost = cost;
    }

    public Money getAverageCost(){
        return cost.divide(persons.size());
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivityName(Activity activity) {
        this.activity = activity;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Money getCost() {
        return cost;
    }

    public void setCost(Money cost) {
        this.cost = cost;
    }
}
