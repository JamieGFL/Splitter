package propra2.splitter.service;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import propra2.splitter.domain.Gruppe;

import java.util.ArrayList;
import java.util.List;

@Service
public class GruppenService {

    private List<Gruppe> gruppen = new ArrayList<>();

    private void add(Gruppe gruppe){
        gruppen.add(gruppe);
    }

    public Gruppe addGruppe(OAuth2User principle){
        String login = principle.getAttribute("login");
        Gruppe gruppe = Gruppe.erstelleGruppe(login);
        add(gruppe);
        return gruppe;
    }

    

}
