package propra2.splitter.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AusgabenForm {

  @NotNull
  @NotEmpty
  private final String aktivitaet;

  private final String zahler;

  @NotNull
  @NotEmpty
  private final String teilnehmer;
  @NotNull
  @Min(0)
  private final Double betrag;

  public AusgabenForm(String aktivitaet, String zahler, String teilnehmer, Double betrag) {
    this.aktivitaet = aktivitaet;
    this.zahler = zahler;
    this.teilnehmer = teilnehmer;
    this.betrag = betrag;
  }

  public String getAktivitaet() {
    return aktivitaet;
  }

  public String getZahler() {
    return zahler;
  }

  public String getTeilnehmer() {
    return teilnehmer;
  }

  public Double getBetrag() {
    return betrag;
  }
}
