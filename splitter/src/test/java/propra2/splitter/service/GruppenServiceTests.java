package propra2.splitter.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import propra2.splitter.domain.Gruppe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        Gruppe gruppe = service.addGruppe(mkUser("James"));
        assertThat(service.getGruppen().details()).contains(new GruppenDetails(gruppe.getId(),"James", List.of("James")));
    }

    @Test
    @DisplayName("Service kann mehrere Gruppen speichern")
    void test_02(){
        GruppenService service = new GruppenService();
        Gruppe gruppe1 = service.addGruppe(mkUser("James"));
        Gruppe gruppe2 = service.addGruppe(mkUser("GitLisa"));
        Gruppe gruppe3 = service.addGruppe(mkUser("GitMax"));

        assertThat(service.getGruppen().details())
                .containsExactlyInAnyOrder(new GruppenDetails(gruppe1.getId(),"James",List.of("James")),
                                           new GruppenDetails(gruppe2.getId(),"GitLisa",List.of("GitLisa")),
                                           new GruppenDetails(gruppe3.getId(),"GitMax", List.of("GitMax")));
    }
}
