package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;

public class Expense {
    String activityName;
    String payer;
    List<Person> persons;
    Money cost;

    public Expense(String activityName, String payer, List<Person> persons, Money cost) {
        this.activityName = activityName;
        this.payer = payer;
        this.persons = persons;
        this.cost = cost;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
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
