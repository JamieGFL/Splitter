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
        Gruppe gruppe = Gruppe.erstelleGruppe(1, "MaxHub");
        gruppe.addPerson("GitLisa");

        assertThat(gruppe.getPersonen()).contains(new Person("GitLisa", new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    @DisplayName("correct transaction is determined when personB owes personA")
    void test_02(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());

        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("GitLisa"), Money.of(20, "EUR"));
        gruppe.addAusgabeToPerson("Kino","GitLisa",List.of("MaxHub"), Money.of(15, "EUR"));
        List<Transaktion> transaktion = gruppe.getTransaktionen();

        assertThat(transaktion.get(0).transaktionsNachricht()).isEqualTo(personB.getName()+" has to pay "+ personA.getName() + " an amount of EUR 5.00");
    }

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


    @Test
    @DisplayName("getMaxMapBetrag findet den maximalen Betrag aus der nettoBetraegeMap")
    void test_03(){
        Map<Person, Money> nettoBetraege = new HashMap<>();
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());

        nettoBetraege.put(personA, Money.of(100, "EUR"));
        nettoBetraege.put(personB, Money.of(50, "EUR"));

        assertThat(gruppe.getMaxMapBetrag(nettoBetraege)).isEqualTo(personA);
    }

    @Test
    @DisplayName("getMinMapBetrag findet den minimalen Betrag aus der nettoBetraegeMap")
    void test_04(){
        Map<Person, Money> nettoBetraege = new HashMap<>();
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());

        nettoBetraege.put(personA, Money.of(100, "EUR"));
        nettoBetraege.put(personB, Money.of(50, "EUR"));

        assertThat(gruppe.getMinMapBetrag(nettoBetraege)).isEqualTo(personB);
    }


}
