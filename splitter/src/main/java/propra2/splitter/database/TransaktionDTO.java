package propra2.splitter.database;

import org.javamoney.moneta.Money;

public record TransaktionDTO(PersonDTO zahler, PersonDTO zahlungsempfaenger, Money nettoBetrag) {

}
