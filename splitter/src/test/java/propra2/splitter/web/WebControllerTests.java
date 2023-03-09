package propra2.splitter.web;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import propra2.splitter.config.WebSecurityKonfiguration;
import propra2.splitter.helper.WithMockOAuth2User;
import propra2.splitter.service.GruppenService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(WebSecurityKonfiguration.class)
@WebMvcTest
public class WebControllerTests {


  @Autowired
  MockMvc mvc;


  @MockBean
  GruppenService service;


  @Test
  @DisplayName("Die Gruppen Startseite ist erreichbar")
  void test_01() throws Exception {
    mvc.perform(get("/")).andExpect(status().isOk());
  }

}
