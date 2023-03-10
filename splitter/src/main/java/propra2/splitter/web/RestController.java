package propra2.splitter.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import propra2.splitter.service.GruppenService;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final GruppenService service;

    public RestController(GruppenService service) {
        this.service = service;
    }

//    @PostMapping("/api/gruppen")
//    public ResponseEntity addGruppen(@RequestBody LoginForm loginForm) {
//
//        service.addGruppe()
//        return;
//    }
}
