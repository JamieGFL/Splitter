package propra2.splitter.domain;

import org.javamoney.moneta.Money;

public class Schulden {

    Person zahler;
    Person zahlungsEmpfaenger;
    Money betrag;

    public Schulden(Person zahler, Person zahlungsEmpfaenger) {
        this.zahler = zahler;
        this.zahlungsEmpfaenger = zahlungsEmpfaenger;
        this.betrag = berechneSchulden();
    }

    public Money berechneSchulden(){
        Money schulden = Money.of(0, "EUR");
        for(Ausgabe ausgabe : zahlungsEmpfaenger.getAusgaben()){
            if(ausgabe.getPersonen().contains(zahler)){
                schulden = ausgabe.getDurchschnittsKosten();
            }
        }
        return schulden;
    }

    public Person getZahler() {
        return zahler;
    }

    public void setZahler(Person zahler) {
        this.zahler = zahler;
    }

    public Person getZahlungsEmpfaenger() {
        return zahlungsEmpfaenger;
    }

    public void setZahlungsEmpfaenger(Person zahlungsEmpfaenger) {
        this.zahlungsEmpfaenger = zahlungsEmpfaenger;
    }

    public Money getBetrag() {
        return betrag;
    }

    public void setBetrag(Money betrag) {
        this.betrag = betrag;
    }
}
