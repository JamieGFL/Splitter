package propra2.splitter.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class DomainTests {
    @Test
    @DisplayName("Person can be added to Group")
    void test_01(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Group group = Group.createGroup(1, "MaxHub");
        group.addPerson("GitLisa");

        assertThat(group.getPeople()).contains(new Person("GitLisa", new ArrayList<>(), new ArrayList<>()));
    }

//    @Test
//    @DisplayName("correct transaction is determined when personB owes personA")
//    void test_02(){
//        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
//        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
//        Expense expense1 = new Expense(new Activity("Pizza"), personA, List.of(personB), Money.of(20, "EUR"));
//        Expense expense2 = new Expense(new Activity("Kino"), personA, List.of(personB), Money.of(30, "EUR"));
//        Expense expense3 = new Expense(new Activity("Bowling"), personB, List.of(personA), Money.of(40, "EUR"));
//        personA.getExpenses().add(expense1);
//        personA.getExpenses().add(expense2);
//        personB.getExpenses().add(expense3);
//        Transaction transactionAB = new Transaction(personA, personB);
//
//        String requiredTransaction = transactionAB.getRequiredTransaction();
//
//        assertThat(requiredTransaction).isEqualTo(personB.getName()+" has to pay "+ personA.getName() + " an amount of EUR 10.00");
//    }

//    @Test
//    @DisplayName("correct transaction is determined when personA owes personB")
//    void test_03(){
//        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
//        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
//        Expense expense1 = new Expense(new Activity("Pizza"), personA, List.of(personB), Money.of(20, "EUR"));
//        Expense expense2 = new Expense(new Activity("Kino"), personA, List.of(personB), Money.of(10, "EUR"));
//        Expense expense3 = new Expense(new Activity("Bowling"), personB, List.of(personA), Money.of(40, "EUR"));
//        personA.getExpenses().add(expense1);
//        personA.getExpenses().add(expense2);
//        personB.getExpenses().add(expense3);
//        Transaction transactionAB = new Transaction(personA, personB);
//
//        String requiredTransaction = transactionAB.getRequiredTransaction();
//
//        assertThat(requiredTransaction).isEqualTo(personA.getName()+" has to pay "+ personB.getName() + " an amount of EUR 10.00");
//    }
    


}
