package propra2.splitter.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import propra2.splitter.domain.Gruppe;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;

public class GruppenServiceTests {

    private DefaultOAuth2User mkUser(String login) {
        return new DefaultOAuth2User(
                List.of(),
                Map.of("login", login),
                "login");
    }

    @Test
    @DisplayName("Service kann Gruppen hinzufügen")
    void test_01(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        assertThat(service.getGruppen().details()).contains(new GruppenDetails(gruppe.getId(),gruppe.getGruppenName(),"James", List.of("James"), false));
    }

    @Test
    @DisplayName("Service kann mehrere Gruppen speichern")
    void test_02(){
        GruppenService service = new GruppenService();
        Gruppe gruppe1 = service.addGruppe(mkUser("James") ,"Reisegruppe1");
        Gruppe gruppe2 = service.addGruppe(mkUser("GitLisa"), "Reisegruppe2");
        Gruppe gruppe3 = service.addGruppe(mkUser("GitMax"), "Reisegruppe3");

        assertThat(service.getGruppen().details())
                .containsExactlyInAnyOrder(new GruppenDetails(gruppe1.getId(),gruppe1.getGruppenName(),"James",List.of("James"), false),
                                           new GruppenDetails(gruppe2.getId(),gruppe2.getGruppenName(),"GitLisa",List.of("GitLisa"), false),
                                           new GruppenDetails(gruppe3.getId(),gruppe3.getGruppenName(),"GitMax", List.of("GitMax"), false));
    }

    @Test
    @DisplayName("Service kann mehr als eine Person zu einer Gruppe hinzufügen")
    void test_03(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitMax");
        gruppe.addPerson("GitLisa");

        assertThat(service.getGruppen().details())
                .containsExactly(new GruppenDetails(gruppe.getId(),gruppe.getGruppenName(),"James", List.of("James","GitMax","GitLisa"), false));
    }

    @Test
    @DisplayName("Eine Person kann Bestandteil von mehreren Gruppen sein")
    void test_04(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe1");
        Gruppe gruppe2 = service.addGruppe(mkUser("GitLisa"), "Reisegruppe2");
        Gruppe gruppe3 = service.addGruppe(mkUser("GitMax"), "Reisegruppe3");

        gruppe2.addPerson("James");
        gruppe3.addPerson("James");

        assertThat(service.getGruppen().details())
                .containsExactlyInAnyOrder(new GruppenDetails(gruppe.getId(),gruppe.getGruppenName(),"James",List.of("James"), false),
                                           new GruppenDetails(gruppe2.getId(),gruppe2.getGruppenName(),"GitLisa", List.of("GitLisa", "James"), false),
                                           new GruppenDetails(gruppe3.getId(),gruppe3.getGruppenName(),"GitMax", List.of("GitMax", "James"), false));
    }

    @Test
    @DisplayName("Eine Person kann mehrere Gruppen erstellen")
    void test_05(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        Gruppe gruppe2 = service.addGruppe(mkUser("James"), "Reisegruppe");
        Gruppe gruppe3 = service.addGruppe(mkUser("James"), "Reisegruppe");

        assertThat(service.getGruppen().details())
                .containsExactlyInAnyOrder(new GruppenDetails(gruppe.getId(),gruppe.getGruppenName(),"James",List.of("James"), false),
                                           new GruppenDetails(gruppe2.getId(),gruppe2.getGruppenName(),"James",List.of("James"), false),
                                           new GruppenDetails(gruppe3.getId(),gruppe3.getGruppenName(),"James",List.of("James"), false));
    }

    @Test
    @DisplayName("Es werden einem nur Gruppen angezeigt wo man auch Mitglied ist")
    void test_06(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        Gruppe gruppe2 = service.addGruppe(mkUser("GitLisa"), "Reisegruppe");
        Gruppe gruppe3 = service.addGruppe(mkUser("GitMax"), "Reisegruppe");

        gruppe2.addPerson("James");

        assertThat(service.personToGruppeMatch(mkUser("James")).details())
                .containsExactlyInAnyOrder(new GruppenDetails(gruppe.getId(),gruppe.getGruppenName(),"James", List.of("James"), false),
                                           new GruppenDetails(gruppe2.getId(),gruppe2.getGruppenName(), "GitLisa", List.of("GitLisa", "James"), false));
    }

    @Test
    @DisplayName("Service kann nach beliebigen Gruppen durch die ID filtern")
    void test_07(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        Gruppe gruppe2 = service.addGruppe(mkUser("GitLisa"), "Reisegruppe");
        Gruppe gruppe3 = service.addGruppe(mkUser("GitMax"), "Reisegruppe");
        Gruppe gruppe4 = service.addGruppe(mkUser("ErixHub"), "Reisegruppe");

        assertThat(service.getSingleGruppe(gruppe3.getId())).isEqualTo(gruppe3);
    }

    @Test
    @DisplayName("Der Service kann Ausgaben eintragen")
    void test_08(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitLisa");
        Double d = 40.00;

        service.addAusgabeToGruppe(gruppe.getId(),"pizza","James","James, GitLisa", d);

        assertThat(service.getSingleGruppe(gruppe.getId()).getGruppenAusgaben()).hasSize(1);
    }

    @Test
    @DisplayName("Der Service kann mehrere Ausgaben eintragen")
    void test_09(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitLisa");
        Double d = 40.00;

        service.addAusgabeToGruppe(gruppe.getId(),"pizza","James","James, GitLisa", d);
        service.addAusgabeToGruppe(gruppe.getId(),"club","James","James, GitLisa", d);

        assertThat(service.getSingleGruppe(gruppe.getId()).getGruppenAusgaben()).hasSize(2);
    }

    @Test
    @DisplayName("Service kann Transaktionen für eine Gruppe hinzufügen")
    void test_10(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitLisa");
        Double d = 40.00;

        service.addAusgabeToGruppe(gruppe.getId(),"pizza","James","James, GitLisa", d);
        service.transaktionBerechnen(gruppe.getId());

        assertThat(service.getSingleGruppe(gruppe.getId()).getTransaktionen()).hasSize(1);
    }

    @Test
    @DisplayName("Service speichert nur Transaktionsnachricht für Gesamtheit der Ausgaben")
    void test_11(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitLisa");
        Double d = 40.00;

        service.addAusgabeToGruppe(gruppe.getId(),"pizza","James","James, GitLisa", d);
        service.transaktionBerechnen(gruppe.getId());
        service.addAusgabeToGruppe(gruppe.getId(),"club","James","James, GitLisa", d);
        service.transaktionBerechnen(gruppe.getId());

        assertThat(service.getSingleGruppe(gruppe.getId()).getTransaktionen()).hasSize(1);
    }

    @Test
    @DisplayName("Service kann Gruppen schließen")
    void test_12(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitLisa");

        service.closeGruppe(gruppe.getId());

        assertThat(service.getSingleGruppe(gruppe.getId()).isGeschlossen()).isTrue();

    }

    @Test
    @DisplayName("Nicht geschlossene Gruppen sind offen")
    void test_13(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"), "Reisegruppe");
        gruppe.addPerson("GitLisa");


        assertThat(service.getSingleGruppe(gruppe.getId()).isGeschlossen()).isFalse();

    }


}
