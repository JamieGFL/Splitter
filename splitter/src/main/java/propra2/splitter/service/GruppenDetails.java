package propra2.splitter.service;

import java.util.List;
import java.util.UUID;

public record GruppenDetails(UUID id,String gruppenName, List<String> personen, boolean geschlossen) {

}
