package propra2.splitter.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import propra2.splitter.config.WebSecurityKonfiguration;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.helper.WithMockOAuth2User;
import propra2.splitter.service.GruppenService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(WebSecurityKonfiguration.class)
public class AddTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    GruppenService service;

    @Test
    @WithMockOAuth2User(login = "MaxHub")
    @DisplayName("Eine valide Gruppe wird hinzugefügt")
    void test_01() throws Exception {

        when(service.addGruppe(any(), anyString())).thenReturn(Gruppe.erstelleGruppe("MaxHub","Gruppe"));

        String gruppenName = "Gruppe";

        mvc.perform(post("/add")
                .param("gruppenName" ,gruppenName)
                .with(csrf())).andExpect(status().is3xxRedirection());

        ArgumentCaptor<OAuth2User> captor = ArgumentCaptor.forClass(OAuth2User.class);
        verify(service).addGruppe(captor.capture(), eq(gruppenName));
        assertThat((String) captor.getValue().getAttribute("login")).isEqualTo("MaxHub");

    }

    @Test
    @WithMockOAuth2User(login = "MaxHub")
    @DisplayName("Eine Person wird zu einer Gruppe hinzugefügt")
    void test_02() throws Exception{

        Gruppe gruppe = Gruppe.erstelleGruppe("MaxHub", "Reisegruppe");

        mvc.perform(post("/gruppe/add")
                .param("id", gruppe.getId().toString())
                .param("loginForm", "Gitlisa")
                .with(csrf())).andExpect(status().is3xxRedirection());

        verify(service).addPersonToGruppe(gruppe.getId(),"Gitlisa");

    }

    @Test
    @WithMockOAuth2User(login = "MaxHub")
    @DisplayName("Eine Person mit invalidem Github Namen wird nicht hinzugefügt")
    void test_03() throws Exception{

        Gruppe gruppe = Gruppe.erstelleGruppe("MaxHub", "Reisegruppe");

        mvc.perform(post("/gruppe/add")
                .param("id", gruppe.getId().toString())
                .param("loginForm", "ad")
                .with(csrf())).andExpect(status().is3xxRedirection());

        verify(service, never()).addPersonToGruppe(any(), anyString());

    }

    @Test
    @WithMockOAuth2User(login = "MaxHub")
    @DisplayName("Ausgaben können hinzugefügt werden")
    void test_04() throws Exception{

        Gruppe gruppe = Gruppe.erstelleGruppe("MaxHub", "Reisegruppe");

        mvc.perform(post("/gruppe/add/ausgaben")
                .param("id", gruppe.getId().toString())
                .param("aktivitaet", "pizza")
                .param("zahler", "MaxHub")
                .param("teilnehmer", "MaxHub")
                .param("betrag", "40.00")
                .with(csrf())).andExpect(status().is3xxRedirection());

        verify(service).addAusgabeToGruppe(gruppe.getId(), "pizza", "MaxHub", "MaxHub", 40.00);

    }

    //Validation Tests hier noch machen!!!

    @Test
    @WithMockOAuth2User(login = "MaxHub")
    @DisplayName("Transaktionen werden berechnet")
    void test_09() throws Exception{

        Gruppe gruppe = Gruppe.erstelleGruppe("MaxHub", "Reisegruppe");

        mvc.perform(post("/gruppe/add/ausgaben/transaktion")
                .param("id", gruppe.getId().toString())
                .with(csrf())).andExpect(status().is3xxRedirection());

        verify(service).transaktionBerechnen(gruppe.getId());

    }

    @Test
    @WithMockOAuth2User(login = "MaxHub")
    @DisplayName("Gruppen werden geschlossen")
    void test_10() throws Exception{

        Gruppe gruppe = Gruppe.erstelleGruppe("MaxHub", "Reisegruppe");

        mvc.perform(post("/gruppe/close")
                .param("id", gruppe.getId().toString())
                .with(csrf())).andExpect(status().is3xxRedirection());

        verify(service).closeGruppe(gruppe.getId());

    }


}
