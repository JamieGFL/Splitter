package propra2.splitter.web;


import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.service.GruppenOnPage;
import propra2.splitter.service.GruppenService;

@Controller
public class WebController {

    private final GruppenService service;

    public WebController(GruppenService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String gruppenSeite(Model model, OAuth2AuthenticationToken token){
        GruppenOnPage liste = service.getGruppen();
        model.addAttribute("gruppen", liste);
        return "index";
    }

    @PostMapping("/")
    public String addGruppen(OAuth2AuthenticationToken token){
        Gruppe gruppe = service.addGruppe(token.getPrincipal());
        return "redirect:/";
    }


}
