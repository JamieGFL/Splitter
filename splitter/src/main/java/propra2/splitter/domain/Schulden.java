package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schulden)) return false;
        Schulden schulden = (Schulden) o;
        return Objects.equals(getZahler(), schulden.getZahler()) && Objects.equals(getZahlungsEmpfaenger(), schulden.getZahlungsEmpfaenger()) && Objects.equals(getBetrag(), schulden.getBetrag());
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
