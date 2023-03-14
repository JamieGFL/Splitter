package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;
import propra2.splitter.stereotypes.Wertobjekt;

@Wertobjekt
public class Schulden {

  final Person zahler;
  final Person zahlungsEmpfaenger;
  final Money betrag;

  Schulden(Person zahler, Person zahlungsEmpfaenger) {
    this.zahler = zahler;
    this.zahlungsEmpfaenger = zahlungsEmpfaenger;
    this.betrag = berechneSchulden();
  }

  Money berechneSchulden() {
    Money schulden = Money.of(0, "EUR");
    for (Ausgabe ausgabe : zahlungsEmpfaenger.getAusgaben()) {
      if (ausgabe.getPersonen().contains(zahler)) {
        schulden = ausgabe.getDurchschnittsKosten();
      }
    }
    return schulden;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (!(o instanceof Schulden)) {
          return false;
      }
    Schulden schulden = (Schulden) o;
    return Objects.equals(getZahler(), schulden.getZahler()) && Objects.equals(
        getZahlungsEmpfaenger(), schulden.getZahlungsEmpfaenger()) && Objects.equals(getBetrag(),
        schulden.getBetrag());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getZahler(), getZahlungsEmpfaenger(), getBetrag());
  }

  Person getZahler() {
    return zahler;
  }

  Person getZahlungsEmpfaenger() {
    return zahlungsEmpfaenger;
  }

  Money getBetrag() {
    return betrag;
  }
}
