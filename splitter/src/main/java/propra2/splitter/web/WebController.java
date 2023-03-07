package propra2.splitter.web;


import jakarta.validation.Valid;
import org.javamoney.moneta.Money;
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
    public String addGruppen(OAuth2AuthenticationToken token){
        Gruppe gruppe = service.addGruppe(token.getPrincipal());

        UUID id = gruppe.getId();

        return "redirect:/gruppe?id="+id;
    }

    @GetMapping("/gruppe")
    public String getSingleGruppePage(Model model,
                                      @RequestParam(name = "id", value = "id", required = false) UUID id, @ModelAttribute("loginForm") LoginForm loginForm){
        Gruppe gruppe = service.getSingleGruppe(id);
        model.addAttribute("gruppe", gruppe);


        return "gruppe";
    }

    @PostMapping("/gruppe/add")
    public String addPersonToSingleGruppe(@RequestParam(name = "id", value = "id", required = false) UUID id, @Valid LoginForm loginForm,BindingResult bindingResult,RedirectAttributes attributes){


        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.LoginForm", bindingResult);
            attributes.addFlashAttribute("loginForm", loginForm);

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


}
