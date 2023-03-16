package propra2.splitter.database;

import java.util.List;
import org.javamoney.moneta.Money;

public record AusgabeDTO(AktivitaetDTO aktivitaet, AuslegerDTO ausleger, List<TeilnehmerDTO> personen, double kosten) {

}
