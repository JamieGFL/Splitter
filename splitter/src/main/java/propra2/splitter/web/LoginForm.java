package propra2.splitter.web;

import jakarta.validation.constraints.Pattern;

import java.util.List;

public class LoginForm {

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,15}$", message = "Invalider Githubname")
    private String login;

    public LoginForm(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

}
