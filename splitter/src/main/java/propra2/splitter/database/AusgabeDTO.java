package propra2.splitter.database;

import java.util.List;
import org.javamoney.moneta.Money;

public record AusgabeDTO(AktivitaetDTO aktivitaet, PersonDTO ausleger, List<PersonDTO> personen, Money kosten) {

}
