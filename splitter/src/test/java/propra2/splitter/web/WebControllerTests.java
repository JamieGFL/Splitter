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
import org.springframework.test.web.servlet.MvcResult;
import propra2.splitter.config.WebSecurityKonfiguration;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.helper.WithMockOAuth2User;
import propra2.splitter.service.GruppenDetails;
import propra2.splitter.service.GruppenOnPage;
import propra2.splitter.service.GruppenService;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.CoreMatchers.containsString;

@Import(WebSecurityKonfiguration.class)
@WebMvcTest(WebController.class)
public class WebControllerTests {


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


//  @Test
//  @WithMockOAuth2User(login = "MaxHub")
//  @DisplayName("Gruppen werden auf der Startseite angezeigt")
//  void test_03() throws Exception {
//
//    when(service.personToGruppeMatch(any())).thenReturn(new GruppenOnPage(List.of(new GruppenDetails(
//        UUID.randomUUID(), "Gruppe", "MaxHub", List.of("MaxHub"), false))));
//
//
//    MvcResult result = mvc.perform(get("/")).andReturn();
//    assertThat(result.getResponse().getContentAsString()).contains("Gruppe");
//    assertThat(result.getResponse().getContentAsString()).contains("MaxHub");
//
//  }




}
