package propra2.splitter.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String gruppenSeite(){
        return "index";
    }


}
