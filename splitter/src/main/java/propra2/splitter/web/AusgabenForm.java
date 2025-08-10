package propra2.splitter.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AusgabenForm(@NotNull @NotEmpty String aktivitaet, String zahler,
                           @NotNull @NotEmpty String teilnehmer, @NotNull @Min(0) Double betrag) {

}
