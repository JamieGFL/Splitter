package propra2.splitter.service;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RestGruppenService {

  private final List<Gruppe> gruppen = new ArrayList<>();

  private Integer add(Gruppe gruppe) {
    gruppen.add(gruppe);
    return gruppe.getId();
  }

  public Gruppe getSingleGruppe(Integer id) {
    return gruppen.stream().filter(g -> g.getId().equals(id)).reduce((a, b) -> {
      throw new IllegalArgumentException();
    }).orElseThrow();
  }

  public Integer addRestGruppe(GruppeEntity gruppe) {
    return add(Gruppe.erstelleRestGruppe(gruppe.getGruppe(), gruppe.getName(), gruppe.getPersonen()));
  }

  public List<GruppeEntity> getRestGruppen() {
    return gruppen.stream().map(this::toGruppeEntity).toList();
  }

  private GruppeEntity toGruppeEntity(Gruppe gruppe) {
    return new GruppeEntity(gruppe.getId(), gruppe.getGruppenName(),
        gruppe.getPersonen().stream().map(Person::getName).toList());
  }

  public GruppeInformationEntity getGruppeInformationEntity(Integer id) {
    try {
      Gruppe gruppe = gruppen.stream().filter(g -> g.getId().equals(id)).reduce((a, b) -> {
        throw new IllegalArgumentException();
      }).orElse(null);
      if (gruppe == null) {
        return null;
      }
      return toGruppeInformationsEntity(gruppe);
    } catch (Exception exception) {
      return null;
    }
  }

  public GruppeInformationEntity toGruppeInformationsEntity(Gruppe gruppe) {
    return new GruppeInformationEntity(gruppe.getId(), gruppe.getGruppenName(),
        gruppe.getPersonen().stream().map(Person::getName).toList(),
        gruppe.isGeschlossen(), gruppe.getGruppenAusgaben().stream().
        map(ausgabe -> new AusgabeEntity(ausgabe.getAktivitaetName(), ausgabe.getAuslegerName(),
            ausgabe.getPersonenNamen(), ausgabe.getGesamtKosten().getNumber().intValue() * 100))
        .toList());
  }

  public String setRestGruppeGeschlossen(Integer id) {
    Gruppe gruppe = getSingleGruppe(id);
    gruppe.closeGroup();
    return gruppe.getGruppenName() + " wurde geschlossen";
  }

  public void addRestAusgabenToGruppe(Integer id, AusgabeEntity ausgabenEntity) {
    Gruppe gruppe = getSingleGruppe(id);
    gruppe.addAusgabeToPerson(ausgabenEntity.grund(), ausgabenEntity.glaeubiger(),
        ausgabenEntity.schuldner(), Money.of(ausgabenEntity.cent() / 100, "EUR"));
  }

  public List<TransaktionEntity> getRestTransaktionen(Integer id) {
    Gruppe gruppe = getSingleGruppe(id);
    gruppe.berechneTransaktionen();
    return gruppe.getTransaktionenCopy().stream()
        .map(transaktion -> new TransaktionEntity
            (transaktion.getPerson1Name(), transaktion.getPerson2Name(),
                transaktion.getNettoBetrag().getNumberStripped().intValue() * 100)).toList();
  }

  public List<GruppeEntity> personRestMatch(String login) {
    List<GruppeEntity> currentDetails = getRestGruppen();

    return currentDetails.stream()
        .filter(groupDetails -> groupDetails.getPersonen().stream()
            .anyMatch(Person -> Objects.equals(Person, login))).toList();
  }


}
