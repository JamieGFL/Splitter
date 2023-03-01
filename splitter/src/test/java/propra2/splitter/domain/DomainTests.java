package propra2.splitter.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("correct transaction is determined when personB owes personA")
    void test_05(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(0.00, "EUR"));
        Person personB = new Person("GitLisa", new ArrayList<>(), Money.of(0.00, "EUR"));
        Expense expense1 = new Expense(new Activity("Pizza"), personA, List.of(personB), Money.of(20, "EUR"));
        Expense expense2 = new Expense(new Activity("Kino"), personA, List.of(personB), Money.of(30, "EUR"));
        Expense expense3 = new Expense(new Activity("Bowling"), personB, List.of(personA), Money.of(40, "EUR"));
        personA.expenses().add(expense1);
        personA.expenses().add(expense2);
        personB.expenses().add(expense3);
        Transaction transactionAB = new Transaction(personA, personB);

        String requiredTransaction = transactionAB.getRequiredTransaction();

        assertThat(requiredTransaction).isEqualTo(personB.name()+" has to pay "+ personA.name() + " an amount of EUR 10.00");
    }

    @Test
    @DisplayName("correct transaction is determined when personA owes personB")
    void test_06(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(0.00, "EUR"));
        Person personB = new Person("GitLisa", new ArrayList<>(), Money.of(0.00, "EUR"));
        Expense expense1 = new Expense(new Activity("Pizza"), personA, List.of(personB), Money.of(20, "EUR"));
        Expense expense2 = new Expense(new Activity("Kino"), personA, List.of(personB), Money.of(10, "EUR"));
        Expense expense3 = new Expense(new Activity("Bowling"), personB, List.of(personA), Money.of(40, "EUR"));
        personA.expenses().add(expense1);
        personA.expenses().add(expense2);
        personB.expenses().add(expense3);
        Transaction transactionAB = new Transaction(personA, personB);

        String requiredTransaction = transactionAB.getRequiredTransaction();

        assertThat(requiredTransaction).isEqualTo(personA.name()+" has to pay "+ personB.name() + " an amount of EUR 10.00");
    }

    @Test
    @DisplayName("correct transaction is determined when both persons don't owe each other anything")
    void test_07(){
        Person personA = new Person("MaxHub", new ArrayList<>(), Money.of(0.00, "EUR"));
        Person personB = new Person("GitLisa", new ArrayList<>(), Money.of(0.00, "EUR"));
        Expense expense1 = new Expense(new Activity("Pizza"), personA, List.of(personB), Money.of(20, "EUR"));
        Expense expense2 = new Expense(new Activity("Kino"), personA, List.of(personB), Money.of(20, "EUR"));
        Expense expense3 = new Expense(new Activity("Bowling"), personB, List.of(personA), Money.of(40, "EUR"));
        personA.expenses().add(expense1);
        personA.expenses().add(expense2);
        personB.expenses().add(expense3);
        Transaction transactionAB = new Transaction(personA, personB);

        String requiredTransaction = transactionAB.getRequiredTransaction();

        assertThat(requiredTransaction).isEqualTo(personA.name() +" and "+ personB.name() +" don't owe anything to each other");
    }


}
