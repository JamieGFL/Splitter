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





}
