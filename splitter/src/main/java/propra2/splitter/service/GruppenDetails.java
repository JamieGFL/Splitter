package propra2.splitter.service;

import java.util.List;
import java.util.UUID;

public record GruppenDetails(UUID id, String gruender, List<String> personen, boolean geschlossen) {

}
