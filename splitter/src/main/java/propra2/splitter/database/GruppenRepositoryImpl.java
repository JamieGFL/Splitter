package propra2.splitter.database;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.service.GruppenRepository;

@Repository
public class GruppenRepositoryImpl implements GruppenRepository {


  @Override
  public List<Gruppe> findAll() {
    return null;
  }

  @Override
  public Optional<Gruppe> findById(Integer id) {
    return Optional.empty();
  }

  @Override
  public Gruppe save(Gruppe gruppe) {
    return null;
  }
}
