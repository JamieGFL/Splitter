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

  private final GruppenRepository repository;

  public RestGruppenService(GruppenRepository repository) {
    this.repository = repository;
  }

  public Gruppe getSingleGruppe(Integer id) {
    return repository.findById(id).orElse(null);
  }

  public Integer addRestGruppe(GruppeEntity gruppe) {
    return repository.save(Gruppe.erstelleRestGruppe(null, gruppe.getName(), gruppe.getPersonen())).getId();
  }

  public List<GruppeEntity> getRestGruppen() {
    List<Gruppe> gruppen = repository.findAll();
    return gruppen.stream().map(this::toGruppeEntity).toList();
  }

  private GruppeEntity toGruppeEntity(Gruppe gruppe) {
    return new GruppeEntity(gruppe.getId(), gruppe.getGruppenName(),
            gruppe.getPersonen().stream().map(Person::getName).toList());
  }

  public GruppeInformationEntity getGruppeInformationEntity(Integer id) {
    if (getSingleGruppe(id) == null) {
      return null;
    }
    Gruppe gruppe = getSingleGruppe(id);
    return toGruppeInformationsEntity(gruppe);
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
    repository.save(gruppe);
    return gruppe.getGruppenName() + " wurde geschlossen";
  }

  public void addRestAusgabenToGruppe(Integer id, AusgabeEntity ausgabenEntity) {
    Gruppe gruppe = getSingleGruppe(id);
    gruppe.addAusgabeToPerson(ausgabenEntity.grund(), ausgabenEntity.glaeubiger(),
            ausgabenEntity.schuldner(), Money.of(ausgabenEntity.cent() / 100, "EUR"));
    repository.save(gruppe);
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
