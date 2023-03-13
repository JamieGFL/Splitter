package propra2.splitter.web;

import org.apache.el.parser.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;
import propra2.splitter.config.WebSecurityKonfiguration;
import propra2.splitter.helper.WithMockOAuth2User;
import propra2.splitter.service.GruppenDetails;
import propra2.splitter.service.GruppenOnPage;
import propra2.splitter.service.GruppenService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Import(WebSecurityKonfiguration.class)
@WebMvcTest(WebController.class)
public class GruppenAnzeigeTest {


  @Autowired
  MockMvc mvc;


  @MockBean
  GruppenService service;


  @Test
  @WithMockOAuth2User(login = "MaxHub")
  @DisplayName("Die Gruppen Startseite ist erreichbar")
  void test_01() throws Exception {

    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));

  }

  @Test
  @WithMockOAuth2User(login = "MaxHub")
  @DisplayName("Nur die Gruppen in denen man auch Mitglied ist werden einem angezeigt")
  void test_02() throws Exception{

    when(service.personToGruppeMatch(any()))
            .thenReturn(new GruppenOnPage(List.of(new GruppenDetails(UUID.randomUUID(), "Reisegruppe", List.of("MaxHub"), false))));

    MvcResult result = mvc.perform(get("/")).andReturn();

    assertThat(result.getResponse().getContentAsString()).contains("Reisegruppe");

  }

  @Test
  @WithMockOAuth2User(login = "MaxHub")
  @DisplayName("Jede Gruppe hat einen Link für Gruppeninformationen")
  void test_03() throws Exception{

    when(service.personToGruppeMatch(any()))
            .thenReturn(new GruppenOnPage(List.of(new GruppenDetails(UUID.randomUUID(), "Reisegruppe", List.of("MaxHub"), false))));

    MvcResult result = mvc.perform(get("/")).andReturn();

    assertThat(result.getResponse().getContentAsString()).contains("Anzeigen");

  }

  @Test
  @WithMockOAuth2User(login = "MaxHub")
  @DisplayName("Das Eingabeformular für den Gruppennamen wird angezeigt")
  void test_04() throws Exception{

    when(service.personToGruppeMatch(any()))
            .thenReturn(new GruppenOnPage(List.of(new GruppenDetails(UUID.randomUUID(), "Reisegruppe",  List.of("MaxHub"), false))));

    MvcResult result = mvc.perform(get("/")).andReturn();

    String html = result.getResponse().getContentAsString();

    assertThat(html).contains("<form method=\"post\" action=\"/add\">");
    assertThat(html).contains("<input type=\"text\" name=\"gruppenName\" >");

  }

  @Test
  @WithMockOAuth2User(login = "MaxHub")
  @DisplayName("Die Gruppeninformationsseite ist verlinkt")
  void test_05() throws Exception{
    UUID id = UUID.randomUUID();

    when(service.personToGruppeMatch(any()))
            .thenReturn(new GruppenOnPage(List.of(new GruppenDetails(id, "Reisegruppe", List.of("MaxHub"), false))));

    MvcResult result = mvc.perform(get("/")).andReturn();

    String html = result.getResponse().getContentAsString();
    String idToString = id.toString();

    assertThat(html).contains("<a href=\"gruppe?id="+ idToString +"\"> Anzeigen </a>");

  }


  @Test
  @WithMockOAuth2User(login = "MaxHub")
  @DisplayName("Geschlossene Gruppen in denen man Mitglied war werden einem Seperat angezeigt")
  void test_06() throws Exception{
    UUID id = UUID.randomUUID();

    when(service.personToGruppeMatch(any()))
        .thenReturn(new GruppenOnPage(List.of(new GruppenDetails(id, "Reisegruppe", "MaxHub", List.of("MaxHub"), true))));

    MvcResult result = mvc.perform(get("/")).andReturn();

    String html = result.getResponse().getContentAsString();
    String idToString = id.toString();

    assertThat(html).contains("Geschlossene Gruppen");
    assertThat(html).contains("Reisegruppe");
    assertThat(html).contains("MaxHub");
    assertThat(html).contains("Geschlossen");

  }

}
