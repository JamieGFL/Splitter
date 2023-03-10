package propra2.splitter.web;

import jakarta.validation.constraints.Pattern;

import java.util.List;

public class LoginForm {

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,15}$", message = "Invalider Githubname")
    private String login;
    private String name;
    private List<String> personen;

    public LoginForm(String login) {
        this.login = login;
    }

    public LoginForm(String name, List<String> personen){
        this.name = name;
        this.personen = personen;
    }

    public String getLogin() {
        return login;
    }

}
