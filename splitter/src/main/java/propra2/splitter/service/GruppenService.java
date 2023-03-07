package propra2.splitter.service;

import org.javamoney.moneta.Money;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.domain.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class GruppenService {

    private List<Gruppe> gruppen = new ArrayList<>();

    private void add(Gruppe gruppe){
        gruppen.add(gruppe);
    }

    public Gruppe addGruppe(OAuth2User principle){
        String login = principle.getAttribute("login");
        Gruppe gruppe = Gruppe.erstelleGruppe(login);
        add(gruppe);
        return gruppe;
    }

    private GruppenDetails toGruppenDetails(Gruppe gruppe){
        return new GruppenDetails(gruppe.getId(),gruppe.getCreator().getName(),gruppe.getPersonen().stream().map(Person::getName).toList());
    }

    public GruppenOnPage getGruppen(){
        List<GruppenDetails> gruppenDetails = gruppen.stream().map(this::toGruppenDetails).toList();
        return new GruppenOnPage(gruppenDetails);
    }

    public Gruppe getSingleGruppe(UUID id){
        return gruppen.stream().filter(e -> e.getId().equals(id)).reduce((a,b) -> {
            throw new IllegalArgumentException();
        }).orElseThrow();
    }

    public void addPersonToGruppe(UUID id, String login){
        Gruppe gruppe = getSingleGruppe(id);
        gruppe.addPerson(login);
    }

    public void addAusgabeToGruppe(UUID id, String aktivitaet, String login,String teilnehmer ,Double cost){
        Gruppe gruppe = getSingleGruppe(id);

        gruppe.addAusgabeToPerson(aktivitaet,login, Arrays.stream(teilnehmer.split(", ")).toList(),Money.of(cost, "EUR"));
    }

    public void transaktionBerechnen(UUID id){
        Gruppe gruppe = getSingleGruppe(id);
        gruppe.berechneTransaktionen();
    }


}
