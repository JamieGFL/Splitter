package propra2.splitter.database;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.service.GruppenRepository;

@Repository
public class GruppenRepositoryImpl implements GruppenRepository {

  private final SpringDataGruppeRepository repository;

  public GruppenRepositoryImpl(SpringDataGruppeRepository repository) {
    this.repository = repository;
  }


  @Override
  public List<Gruppe> findAll() {
    List<GruppeDTO> all = repository.findAll();
    return all.stream().map(this::toGruppe).toList();
  }

  @Override
  public Optional<Gruppe> findById(Integer id) {
    return Optional.empty();
  }

  @Override
  public Gruppe save(Gruppe gruppe) {
    return null;
  }


  private Gruppe toGruppe(GruppeDTO dto){
    Gruppe gruppe = new Gruppe(dto.id(), dto.gruppenName());
    dto.personen().forEach(p -> gruppe.addPersonAlways(p.name()));

    return gruppe;
  }

}
