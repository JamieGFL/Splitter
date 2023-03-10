package propra2.splitter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import propra2.splitter.service.GruppenOnPage;
import propra2.splitter.service.GruppenService;

import java.util.UUID;

    @org.springframework.web.bind.annotation.RestController
    public class RestController {

        private final GruppenService service;

        public RestController(GruppenService service) {
            this.service = service;
        }

        @GetMapping("/api/user/{login}/gruppen")
        public String gruppenSeite(@PathVariable String login){

            return "index";
        }

      @PostMapping("/api/gruppen")
      public ResponseEntity<UUID> addGruppen(@RequestBody GruppenForm gruppenForm) {
        return new ResponseEntity<>(service.addRestGruppe(gruppenForm.getName(), gruppenForm.getPersonen()), HttpStatus.CREATED);
        }

}
