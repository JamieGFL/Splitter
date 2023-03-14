package propra2.splitter.web;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import propra2.splitter.config.WebSecurityKonfiguration;
import propra2.splitter.service.AusgabeEntity;
import propra2.splitter.service.GruppeEntity;
import propra2.splitter.service.GruppeInformationEntity;
import propra2.splitter.service.RestGruppenService;

@WebMvcTest(controllers = RestController.class)
@Import(WebSecurityKonfiguration.class)
public class RestControllerTests {


  @Autowired
  MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  RestGruppenService service;



  @Test
  @DisplayName("Gruppen können erstellt werden")
  void test_01() throws Exception {

    GruppeEntity entity = new GruppeEntity("Reisen", List.of("MaxHub"));

    mvc.perform(MockMvcRequestBuilders.post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(entity))).andExpect(status().isCreated());

  }

  @Test
  @DisplayName("BadRequest weil die Gruppe keine Mitglieder hat")
  void test_02() throws Exception {

    GruppeEntity entity = new GruppeEntity("Reisen", List.of());

    mvc.perform(MockMvcRequestBuilders.post("/api/gruppen")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(entity))).andExpect(status().isBadRequest());

  }

  @Test
  @DisplayName("BadRequest weil die Gruppe keinen zulässigen Namen hat")
  void test_03() throws Exception {

    GruppeEntity entity = new GruppeEntity(null, List.of("MaxHub"));

    mvc.perform(MockMvcRequestBuilders.post("/api/gruppen")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(entity))).andExpect(status().isBadRequest());

  }



  @Test
  @DisplayName("status OK wird zurück gegeben wenn eine Gruppe für eine bestimmte Person angezeigt wird")
  void test_04() throws Exception {

    GruppeEntity entity = new GruppeEntity("Reisen", List.of("MaxHub"));
    when(service.personRestMatch(anyString())).thenReturn(Collections.singletonList(entity));

    mvc.perform(MockMvcRequestBuilders.get("/api/user/{githublogin}/gruppen", "MaxHub").accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk());
  }


  @Test
  @DisplayName("Gruppeninformation für die jeweilige Gruppe ist vorhanden und wirft Status OK")
  void test_05() throws Exception {

    UUID id = UUID.randomUUID();

    AusgabeEntity ausgabe = new AusgabeEntity("Pizza", "MaxHub", List.of("MaxHub", "GitLisa"),10000);
    GruppeInformationEntity entity =new GruppeInformationEntity(id, "Reisegruppe", List.of("MaxHub", "GitLisa"), false, List.of(ausgabe));

    when(service.getGruppeInformationEntity(String.valueOf(id))).thenReturn(entity);

    mvc.perform(MockMvcRequestBuilders.get("/api/gruppen/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(entity))).andDo(print()).andExpect(status().isOk());

  }

  @Test
  @DisplayName("Gruppeninformation für die jeweilige Gruppe steht im Body von dem Response")
  void test_06() throws Exception {

    UUID id = UUID.randomUUID();

    AusgabeEntity ausgabe = new AusgabeEntity("Pizza", "MaxHub", List.of("MaxHub", "GitLisa"),10000);
    GruppeInformationEntity entity =new GruppeInformationEntity(id, "Reisegruppe", List.of("MaxHub", "GitLisa"), false, List.of(ausgabe));

    when(service.getGruppeInformationEntity(String.valueOf(id))).thenReturn(entity);

    mvc.perform(MockMvcRequestBuilders.get("/api/gruppen/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(entity))).andDo(print())
        .andExpect(content().string(mapper.writeValueAsString(entity)));

  }


//  @Test
//  @DisplayName("Gruppen können geschlossen werden")
//  void test_07() throws Exception {
//
//    UUID id = UUID.randomUUID();
//
//    AusgabeEntity ausgabe = new AusgabeEntity("Pizza", "MaxHub", List.of("MaxHub", "GitLisa"),10000);
//    GruppeInformationEntity entity = new GruppeInformationEntity(id, "Reisegruppe", List.of("MaxHub", "GitLisa"), false, List.of(ausgabe));
//
//    when(service.setRestGruppeGeschlossen(entity.gruppe().toString())).thenReturn("Reisegruppe wurde geschlossen");
//
//    mvc.perform(MockMvcRequestBuilders.post("/api/gruppen/{id}/schliessen", id)
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(content().string("Reisegruppe wurde geschlossen"));
//
//  }


}
