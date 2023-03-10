package propra2.splitter.web;

import java.util.List;

public class GruppenForm {

    private String name;
    private List<String> personen;

    public GruppenForm(String name, List<String> personen) {
        this.name = name;
        this.personen = personen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPersonen() {
        return personen;
    }

    public void setPersonen(List<String> personen) {
        this.personen = personen;
    }
}
