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
    @DisplayName("Person kann Gruppe hinzugefügt werden")
    void test_01(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, "MaxHub");


        gruppe.addPerson("GitLisa");


        assertThat(gruppe.getPersonen().get(1)).isEqualTo(new Person("GitLisa", new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    @DisplayName("Gruppe wird mit korrektem Gründer erstellt")
    void test_02(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());


        Gruppe gruppe = Gruppe.erstelleGruppe(1, "MaxHub");


        assertThat(gruppe.getPersonen().get(0)).isEqualTo(personA);
    }

    @Test
    @DisplayName("Ausgaben kann Person hinzugefügt werden")
    void test_03(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, "MaxHub");
        gruppe.addPerson(personB.getName());


        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("GitLisa"), Money.of(20, "EUR"));


        assertThat(gruppe.getPersonen().get(0).getAusgabe(0)).isEqualTo(new Ausgabe(new Aktivitaet("Pizza"), personA, List.of(personB), Money.of(20, "EUR")));
    }

    @Test
    @DisplayName("Schulden können Person hinzugefügt werden")
    void test_04(){
        Gruppe gruppe = Gruppe.erstelleGruppe(1, "MaxHub");
        gruppe.addPerson("GitLisa");


        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("GitLisa"), Money.of(20, "EUR"));


        assertThat(gruppe.getPersonen().get(1).getSchulden(0)).isEqualTo(new Schulden(gruppe.getPersonen().get(1), gruppe.getPersonen().get(0)));
    }



    @Test
    @DisplayName("Szenario 1: Summieren von Auslagen")
    void test_05(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("MaxHub","GitLisa"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","MaxHub",List.of("MaxHub","GitLisa"), Money.of(20, "EUR"));


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.get(0).getTransaktionsNachricht()).isEqualTo(personB.getName() + " muss EUR 15.00 an " + personA.getName() + " zahlen");
    }

    @Test
    @DisplayName("Szenario 2: Ausgleich")
    void test_06(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("MaxHub","GitLisa"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","GitLisa",List.of("MaxHub","GitLisa"), Money.of(20, "EUR"));


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.get(0).getTransaktionsNachricht()).isEqualTo(personA.getName() + " muss EUR 5.00 an " + personB.getName() + " zahlen");
    }

    @Test
    @DisplayName("Szenario 3: Zahlung ohne eigene Beteiligung")
    void test_07(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("GitLisa"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","MaxHub",List.of("MaxHub","GitLisa"), Money.of(20, "EUR"));


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.get(0).getTransaktionsNachricht()).isEqualTo(personB.getName() + " muss EUR 20.00 an " + personA.getName() + " zahlen");
    }

    @Test
    @DisplayName("Szenario 4: Ringausgleich")
    void test_08(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Person personC = new Person("ErixHub", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addPerson(personC.getName());
        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("MaxHub","GitLisa"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","GitLisa",List.of("GitLisa","ErixHub"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","ErixHub",List.of("ErixHub","MaxHub"), Money.of(10, "EUR"));


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.get(0).getTransaktionsNachricht()).isEqualTo("Es sind keine Ausgleichszahlungen notwendig.");
    }

    @Test
    @DisplayName("Szenario 4: Ringausgleich mit ungleichen Ausgaben")
    void test_09(){
        Person personA = new Person("MaxHub", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("GitLisa", new ArrayList<>(), new ArrayList<>());
        Person personC = new Person("ErixHub", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addPerson(personC.getName());
        gruppe.addAusgabeToPerson("Pizza","MaxHub",List.of("MaxHub","GitLisa"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","GitLisa",List.of("GitLisa","ErixHub"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Kino","ErixHub",List.of("ErixHub","MaxHub"), Money.of(5, "EUR"));


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.get(0).getTransaktionsNachricht()).isEqualTo(personC.getName() + " muss EUR 2.50 an " + personA.getName() + " zahlen");
    }

    @Test
    @DisplayName("Szenario 5: ABC Beispiel aus der Einführung")
    void test_10(){
        Person personA = new Person("Anton", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("Berta", new ArrayList<>(), new ArrayList<>());
        Person personC = new Person("Christian", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addPerson(personC.getName());
        gruppe.addAusgabeToPerson("Pizza","Anton",List.of("Anton","Berta","Christian"), Money.of(60, "EUR"));
        gruppe.addAusgabeToPerson("Kino","Berta",List.of("Anton","Berta", "Christian"), Money.of(30, "EUR"));
        gruppe.addAusgabeToPerson("Kino","Christian",List.of("Berta","Christian"), Money.of(100, "EUR"));
        String transaktion1 = personB.getName() + " muss EUR 30.00 an " + personA.getName() + " zahlen";
        String transaktion2 = personB.getName() + " muss EUR 20.00 an " + personC.getName() + " zahlen";
        String transaktion3 = personB.getName() + " muss EUR 50.00 an " + personA.getName() + " zahlen";
        String transaktion4 = personA.getName() + " muss EUR 20.00 an " + personC.getName() + " zahlen";


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.get(0).getTransaktionsNachricht().equals(transaktion1)|| transaktionen.get(0).getTransaktionsNachricht().equals(transaktion3)).isTrue();
        assertThat(transaktionen.get(1).getTransaktionsNachricht().equals(transaktion2)|| transaktionen.get(1).getTransaktionsNachricht().equals(transaktion4)).isTrue();
    }

    @Test
    @DisplayName("Szenario 6: Beispiel aus der Aufgabenstellung")
    void test_11(){
        Person personA = new Person("A", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("B", new ArrayList<>(), new ArrayList<>());
        Person personC = new Person("C", new ArrayList<>(), new ArrayList<>());
        Person personD = new Person("D", new ArrayList<>(), new ArrayList<>());
        Person personE = new Person("E", new ArrayList<>(), new ArrayList<>());
        Person personF = new Person("F", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addPerson(personC.getName());
        gruppe.addPerson(personD.getName());
        gruppe.addPerson(personE.getName());
        gruppe.addPerson(personF.getName());
        gruppe.addAusgabeToPerson("Hotelzimmer","A",List.of("A","B","C","D","E","F"), Money.of(564, "EUR"));
        gruppe.addAusgabeToPerson("Benzin (Hinweg)","B",List.of("B","A"), Money.of(38.58, "EUR"));
        gruppe.addAusgabeToPerson("Benzin (Rückweg)","B",List.of("B","A","D"), Money.of(38.58, "EUR"));
        gruppe.addAusgabeToPerson("Benzin","C",List.of("C","E","F"), Money.of(82.11, "EUR"));
        gruppe.addAusgabeToPerson("Staedtour","D",List.of("A","B","C","D","E","F"), Money.of(96, "EUR"));
        gruppe.addAusgabeToPerson("Theatervorstellung","F",List.of("B","E","F"), Money.of(95.37, "EUR"));
        String transaction1 = personB.getName() + " muss EUR 96.78 an " + personA.getName() + " zahlen";
        String transaction2 = personC.getName() + " muss EUR 55.26 an " + personA.getName() + " zahlen";
        String transaction3 = personD.getName() + " muss EUR 26.86 an " + personA.getName() + " zahlen";
        String transaction4 = personE.getName() + " muss EUR 169.16 an " + personA.getName() + " zahlen";
        String transaction5 = personF.getName() + " muss EUR 73.79 an " + personA.getName() + " zahlen";


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.stream().map(Transaktion::getTransaktionsNachricht)).containsExactlyInAnyOrder(transaction1, transaction2, transaction3, transaction4, transaction5);
    }


    @Test
    @DisplayName("Szenario 7: Minimierung") //Hier wird ein möglicher Ausgleich ausgerechnet (nicht der, der gegeben wurde), ist jedoch nicht minimal
    void test_12(){
        Person personA = new Person("A", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("B", new ArrayList<>(), new ArrayList<>());
        Person personC = new Person("C", new ArrayList<>(), new ArrayList<>());
        Person personD = new Person("D", new ArrayList<>(), new ArrayList<>());
        Person personE = new Person("E", new ArrayList<>(), new ArrayList<>());
        Person personF = new Person("F", new ArrayList<>(), new ArrayList<>());
        Person personG = new Person("G", new ArrayList<>(), new ArrayList<>());
        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addPerson(personC.getName());
        gruppe.addPerson(personD.getName());
        gruppe.addPerson(personE.getName());
        gruppe.addPerson(personF.getName());
        gruppe.addPerson(personG.getName());
        gruppe.addAusgabeToPerson("Hotelzimmer","D",List.of("D","F"), Money.of(20, "EUR"));
        gruppe.addAusgabeToPerson("Benzin (Hinweg)","G",List.of("B"), Money.of(10, "EUR"));
        gruppe.addAusgabeToPerson("Benzin (Rückweg)","E",List.of("A","C","E"), Money.of(75, "EUR"));
        gruppe.addAusgabeToPerson("Benzin","F",List.of("A","F"), Money.of(50, "EUR"));
        gruppe.addAusgabeToPerson("Staedtour","E",List.of("D"), Money.of(40, "EUR"));
        gruppe.addAusgabeToPerson("Theatervorstellung","F",List.of("B","F"), Money.of(40, "EUR"));
        gruppe.addAusgabeToPerson("Club","F",List.of("C"), Money.of(5, "EUR"));
        gruppe.addAusgabeToPerson("Juan","G",List.of("A"), Money.of(30, "EUR"));
        String transaction1 = personA.getName() + " muss EUR 80.00 an " + personE.getName() + " zahlen";
        String transaction2 = personB.getName() + " muss EUR 30.00 an " + personF.getName() + " zahlen";
        String transaction3 = personC.getName() + " muss EUR 30.00 an " + personG.getName() + " zahlen";
        String transaction4 = personD.getName() + " muss EUR 10.00 an " + personE.getName() + " zahlen";
        String transaction5 = personD.getName() + " muss EUR 10.00 an " + personF.getName() + " zahlen";
        String transaction6 = personD.getName() + " muss EUR 10.00 an " + personG.getName() + " zahlen";


        List<Transaktion> transaktionen = gruppe.getTransaktionen();


        assertThat(transaktionen.stream().map(Transaktion::getTransaktionsNachricht)).containsExactlyInAnyOrder(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6);
    }

    @Test
    @DisplayName("Fehlerabstand von 1 Cent")
    void test_13(){
        Person personA = new Person("A", new ArrayList<>(), new ArrayList<>());
        Person personB = new Person("B", new ArrayList<>(), new ArrayList<>());
        Person personC = new Person("C", new ArrayList<>(), new ArrayList<>());

        Gruppe gruppe = Gruppe.erstelleGruppe(1, personA.getName());
        gruppe.addPerson(personB.getName());
        gruppe.addPerson(personC.getName());

        gruppe.addAusgabeToPerson("Hotelzimmer","A",List.of("A","B","C"), Money.of(100, "EUR"));

        String transaktion1 = personB.getName() + " muss EUR 33.33 an "+ personA.getName() + " zahlen";
        String transaktion2 = personC.getName() + " muss EUR 33.33 an "+ personA.getName() + " zahlen";

        List<Transaktion> transaktionen = gruppe.getTransaktionen();

        assertThat(transaktionen.stream().map(Transaktion::getTransaktionsNachricht))
                .containsExactlyInAnyOrder(transaktion1, transaktion2);
    }

}
