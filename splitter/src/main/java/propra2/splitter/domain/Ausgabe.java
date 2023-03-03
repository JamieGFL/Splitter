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

    public Money getKosten() {
        if(personen.contains(ausleger)){
            return kosten.subtract(getDurchschnittsKosten());
        }else {
            return kosten;
        }
    }

    public Money getDurchschnittsKosten(){
        return kosten.divide(personen.size());
    }

    public Aktivitaet getAktivitaet() {
        return aktivitaet;
    }

    public void setAktivitaetName(Aktivitaet aktivitaet) {
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

    public void setKosten(Money kosten) {
        this.kosten = kosten;
    }
}
