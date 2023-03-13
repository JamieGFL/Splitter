package propra2.splitter.service;

import org.springframework.stereotype.Service;
import propra2.splitter.domain.Gruppe;

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





}
