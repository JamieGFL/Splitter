package propra2.splitter.database;

import org.javamoney.moneta.Money;

public record TransaktionDTO(ZahlerDTO zahler, ZahlungsempfaengerDTO zahlungsempfaenger, double nettoBetrag) {

}
