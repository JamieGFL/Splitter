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
    public void test_01(){
        GruppenService service = new GruppenService();
        Gruppe gruppe = service.addGruppe(mkUser("James"));
        assertThat(service.getGruppen().details()).contains(new GruppenDetails(gruppe.getId(),"James", List.of("James")));
    }
}
