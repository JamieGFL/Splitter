package propra2.splitter.web;

public class AusgabenForm {


  private final String aktivitaet;
  private final String zahler;
  private final String teilnehmer;
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
