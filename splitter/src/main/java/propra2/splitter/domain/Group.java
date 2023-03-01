package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private Integer id;
    private Person creator;
    private List<Person> persons;
    private Integer size = 0;
    boolean closed = false;

    public Group(Integer id, Person creator, List<Person> persons) {
        this.id = id;
        this.creator = creator;
        this.persons = persons;
    }

    public static Group create(Integer id, String creator){
        Person person = new Person(creator, new ArrayList<>(), Money.of(0, "EUR"));
        List<Person> people = new ArrayList<>();
        people.add(person);
        return new Group(id, person, people);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
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
