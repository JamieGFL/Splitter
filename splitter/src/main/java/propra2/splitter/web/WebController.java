package propra2.splitter.web;


import jakarta.validation.Valid;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.service.GruppenOnPage;
import propra2.splitter.service.GruppenService;

import java.util.UUID;

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

    @PostMapping("/add")
    public String addGruppen(OAuth2AuthenticationToken token, String gruppenName){
        Gruppe gruppe = service.addGruppe(token.getPrincipal(), gruppenName);

        UUID id = gruppe.getId();

        return "redirect:/gruppe?id="+id;
    }

    @GetMapping("/gruppe")
    public String getSingleGruppePage(Model model,
                                      @RequestParam(name = "id", value = "id", required = false) UUID id,
                                      @ModelAttribute("loginForm") LoginForm loginForm,
                                      String error, OAuth2AuthenticationToken token){

        if (error != null){
            model.addAttribute("message", error);
        }

        Gruppe gruppe = service.getSingleGruppe(id);
        model.addAttribute("gruppe", gruppe);
        model.addAttribute("login", token.getPrincipal().getAttribute("login"));

        return "gruppe";
    }

    @PostMapping("/gruppe/add")
    public String addPersonToSingleGruppe(@RequestParam(name = "id", value = "id", required = false) UUID id,
                                          @Valid LoginForm loginForm,
                                          BindingResult bindingResult,
                                          RedirectAttributes attributes){


        if (bindingResult.hasErrors()){
            attributes.addAttribute("error", "Invalider GitHub Name");

            return "redirect:/gruppe?id="+id;
        }

        service.addPersonToGruppe(id, loginForm.getLogin());

        return "redirect:/gruppe?id="+id;
    }


    @PostMapping("/gruppe/add/ausgaben")
    public String addAusgabeToGruppe(@RequestParam(name = "id", value = "id", required = false) UUID id, String aktivitaet ,String zahler, String teilnehmer, Double betrag){

        service.addAusgabeToGruppe(id,aktivitaet,zahler,teilnehmer,betrag);

        return "redirect:/gruppe?id="+id;
    }

    @PostMapping("/gruppe/add/ausgaben/transaktion")
    public String berechneTransaktion(@RequestParam(name = "id", value = "id", required = false) UUID id){

        service.transaktionBerechnen(id);

        return "redirect:/gruppe?id="+id;
    }

    @PostMapping("/gruppe/close")
    public String schließGruppe(@RequestParam(name = "id", value = "id", required = false) UUID id){
        service.closeGruppe(id);
        return "redirect:/gruppe?id="+id;
    }


}
