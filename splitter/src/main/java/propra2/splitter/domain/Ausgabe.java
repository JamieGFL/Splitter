package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Objects;

public class Ausgabe {
    Aktivitaet aktivitaet;
    Person ausleger;
    List<Person> personen;
    Money kosten;

    public Ausgabe(Aktivitaet aktivitaet, Person ausleger, List<Person> personen, Money kosten) {
        this.aktivitaet = aktivitaet;
        this.ausleger = ausleger;
        this.personen = personen;
        this.kosten = kosten;
    }

    public Money getKosten() {
        if(personen.contains(ausleger)){
            return kosten.subtract(getDurchschnittsKosten());
        }else {
            return kosten;
        }
    }

    public boolean personPresent(String name){
        if(ausleger.getName().equals(name)){
            return true;
        }
        for (Person person: personen){
            if(person.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ausgabe)) return false;
        Ausgabe ausgabe = (Ausgabe) o;
        return Objects.equals(getAktivitaet(), ausgabe.getAktivitaet()) && Objects.equals(getAusleger(), ausgabe.getAusleger()) && Objects.equals(getPersonen(), ausgabe.getPersonen()) && Objects.equals(getKosten(), ausgabe.getKosten());
    }

    public Money getDurchschnittsKosten(){
        return kosten.divide(personen.size());
    }

    public Aktivitaet getAktivitaet() {
        return aktivitaet;
    }

    public Person getAusleger() {
        return ausleger;
    }

    public List<Person> getPersonen() {
        return personen;
    }
    public List<String> getPersonenNamen() {
        return personen.stream().map(Person::getName).toList();
    }

    public Money getGesamtKosten(){
        return kosten;
    }
}
