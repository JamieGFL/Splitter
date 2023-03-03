package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.List;

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

    public Money getAverageCost(){
        return kosten.divide(personen.size());
    }

    public Aktivitaet getActivity() {
        return aktivitaet;
    }

    public void setActivityName(Aktivitaet aktivitaet) {
        this.aktivitaet = aktivitaet;
    }

    public Person getAusleger() {
        return ausleger;
    }

    public void setAusleger(Person ausleger) {
        this.ausleger = ausleger;
    }

    public List<Person> getPersonen() {
        return personen;
    }

    public void setPersonen(List<Person> personen) {
        this.personen = personen;
    }

    public Money getKosten() {
        return kosten;
    }

    public void setKosten(Money kosten) {
        this.kosten = kosten;
    }
}
