package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Group {
    private Integer id;
    private Person creator;
    private List<Person> people;
    private List<Expense> groupExpenses;
    private List<Transaction> transactions;
    Map<Person,Debt> personDebtMap;
    private Integer size = 0;
    boolean closed = false;

    public Group(Integer id, Person creator, List<Person> people) {
        this.id = id;
        this.creator = creator;
        this.people = people;
    }

    public static Group createGroup(Integer id, String creator){
        Person person = new Person(creator, new ArrayList<>(), new ArrayList<>());
        List<Person> people = new ArrayList<>();
        people.add(person);
        return new Group(id, person, people);
    }

    public void addExpenseToPerson(String activity, String name, List<String> people2, Money cost){
        List<Person> participants = new ArrayList<>();
        for(Person person:people){
            for(String personName : people2){
                if(person.name.equals(personName)){
                    participants.add(person);
                }
            }
        }
        for(Person person : people){
            if(person.name.equals(name)){
                person.expenses.add(new Expense(new Activity(activity),person,participants,cost));
            }
        }
    }

    public void addPerson(String newPerson){
        Person person = new Person(newPerson, new ArrayList<>(), new ArrayList<>());
        people.add(person);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public List<Person> getPeople() {
        return List.copyOf(people);
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
