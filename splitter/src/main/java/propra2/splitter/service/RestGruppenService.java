package propra2.splitter.service;

import org.springframework.stereotype.Service;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RestGruppenService {

    private final List<Gruppe> gruppen = new ArrayList<>();

    private UUID add(Gruppe gruppe){
        gruppen.add(gruppe);
        return gruppe.getId();
    }

    public Gruppe getSingleGruppe(UUID id) {
        return gruppen.stream().filter(g -> g.getId().equals(id)).reduce((a, b) -> {
            throw new IllegalArgumentException();
        }).orElseThrow();
    }

    public UUID addRestGruppe(GruppeEntity gruppe){
        return add(Gruppe.erstelleRestGruppe(gruppe.name(), gruppe.personen()));
    }

    public List<GruppeEntity> getRestGruppen(){
        return gruppen.stream().map(this::toGruppeEntity).toList();
    }

    public GruppeEntity toGruppeEntity(Gruppe gruppe){
        return new GruppeEntity(gruppe.getGruppenName(), gruppe.getPersonen().stream().map(Person::getName).toList());
    }

    public GruppeInformationEntity getGruppeInformationEntity(String id) {
        try{
            UUID uuid = UUID.fromString(id);
            Gruppe gruppe = gruppen.stream().filter(g -> g.getId().equals(uuid)).reduce((a, b) -> {
                throw new IllegalArgumentException();
            }).orElse(null);
            if (gruppe == null){
                System.out.println("Test1");
                return null;
            }
            System.out.println("Test2");
            return toGruppeInformationsEntity(gruppe);
        }
        catch(Exception exception){
            return null;
        }
    }

    public GruppeInformationEntity toGruppeInformationsEntity(Gruppe gruppe){
        return new GruppeInformationEntity(gruppe.getId(), gruppe.getGruppenName(), gruppe.getPersonen().stream().map(Person::getName).toList(),
                gruppe.isGeschlossen(), gruppe.getGruppenAusgaben().stream().
                map(ausgabe -> new AusgabeEntity(ausgabe.getAktivitaet().name(), ausgabe.getAusleger().getName(),
                        ausgabe.getPersonen().stream().map(Person::getName).toList(), ausgabe.getGesamtKosten().getNumber().intValue())).toList());
    }





}
