package propra2.splitter.domain;

import java.util.List;

public class Group {
    Integer id;
    List<Person> persons;
    Integer size = 0;
    boolean closed = false;

    public Group(Integer id, List<Person> persons) {
        this.id = id;
        this.persons = persons;
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
