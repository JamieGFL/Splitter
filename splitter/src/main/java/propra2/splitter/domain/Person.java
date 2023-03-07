package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Objects;

public class Person{

    String name;
    List<Ausgabe> ausgaben;
    List<Schulden> schuldenListe;

    Money nettoBetrag = Money.of(0,"EUR");

    public Person(String name, List<Ausgabe> ausgaben, List<Schulden> schuldenList) {
        this.name = name;
        this.ausgaben = ausgaben;
        this.schuldenListe = schuldenList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getName().equals(person.getName());
    }

    public void addAusgabe(Ausgabe ausgabe){
        ausgaben.add(ausgabe);
    }

    public Ausgabe getAusgabe(int index){
        return ausgaben.get(index);
    }

    public void addSchulden(Schulden schulden){
        schuldenListe.add(schulden);
    }

    public Schulden getSchulden(int index){
        return  schuldenListe.get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ausgabe> getAusgaben() {
        return ausgaben;
    }

    public void setAusgaben(List<Ausgabe> ausgaben) {
        this.ausgaben = ausgaben;
    }

    public List<Schulden> getSchuldenListe() {
        return schuldenListe;
    }

    public void setSchuldenListe(List<Schulden> schuldens) {
        this.schuldenListe = schuldens;
    }

    public Money getNettoBetrag() {
        return nettoBetrag;
    }

    public void setNettoBetrag(Money nettoBetrag) {
        this.nettoBetrag = nettoBetrag;
    }

    //    @Override
//    public int compare(Person o1, Person o2) {
//        if(o1.totalExpense.subtract(o2.totalExpense).isLessThan(Money.of(0, "EUR"))){
//            return -1;
//        }
//        else if(o1.totalExpense.subtract(o2.totalExpense).isEqualTo(Money.of(0, "EUR"))){
//            return 0;
//        } else {
//            return 1;
//        }
//    }
}


