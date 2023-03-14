package propra2.splitter.domain;

import org.javamoney.moneta.Money;
import java.util.List;
import java.util.Objects;
import propra2.splitter.stereotypes.Wertobjekt;

@Wertobjekt
public class Person {

  final String name;
  final List<Ausgabe> ausgaben;
  final List<Schulden> schuldenListe;

  Money nettoBetrag = Money.of(0, "EUR");

  Person(String name, List<Ausgabe> ausgaben, List<Schulden> schuldenList) {
    this.name = name;
    this.ausgaben = ausgaben;
    this.schuldenListe = schuldenList;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (!(o instanceof Person)) {
          return false;
      }
    Person person = (Person) o;
    return getName().equals(person.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  void addAusgabe(Ausgabe ausgabe) {
    ausgaben.add(ausgabe);
  }

  Ausgabe getAusgabe(int index) {
    return ausgaben.get(index);
  }

  void addSchulden(Schulden schulden) {
    schuldenListe.add(schulden);
  }

  Schulden getSchulden(int index) {
    return schuldenListe.get(index);
  }

  public String getName() {
    return name;
  }

  List<Ausgabe> getAusgaben() {
    return ausgaben;
  }

  List<Schulden> getSchuldenListe() {
    return schuldenListe;
  }

  public Money getNettoBetrag() {
    return nettoBetrag;
  }

  public void setNettoBetrag(Money nettoBetrag) {
    this.nettoBetrag = nettoBetrag;
  }
}


