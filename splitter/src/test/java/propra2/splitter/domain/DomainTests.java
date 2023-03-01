package propra2.splitter.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class DomainTests {

    @Test
    @DisplayName("Persons with bigger TotalExpense gets determined correctly")
    void test_01(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(100.00, "EUR"));
        Person personB = new Person("GitLisa", new ArrayList<>(), Money.of(120.00, "EUR"));

        assertThat(personA.compare(personA, personB)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Persons with smaller TotalExpense gets determined correctly")
    void test_02(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(120.00, "EUR"));
        Person personB = new Person("GitLisa", new ArrayList<>(), Money.of(100.00, "EUR"));

        assertThat(personA.compare(personA, personB)).isEqualTo(1);
    }

    @Test
    @DisplayName("Persons with equal TotalExpense gets determined correctly")
    void test_03(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(100.00, "EUR"));
        Person personB = new Person("GitLisa", new ArrayList<>(), Money.of(100.00, "EUR"));

        assertThat(personA.compare(personA, personB)).isEqualTo(0);
    }

    @Test
    @DisplayName("Person can be added to Group")
    void test_04(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(0.00, "EUR"));
        Group group = Group.create(1, "MaxHub");
        group.addPerson("GitLisa");

        assertThat(group.getPersons()).contains(new Person("GitLisa", new ArrayList<>(), Money.of(0.00, "EUR")));
    }
}
