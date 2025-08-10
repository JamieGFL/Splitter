package propra2.splitter.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "transaktion_dto")
public record TransaktionDTO(@Id UUID id,
                             ZahlerDTO zahler,
                             ZahlungsempfaengerDTO zahlungsempfaenger,
                             @Column("netto_betrag") double nettoBetrag) {

}
