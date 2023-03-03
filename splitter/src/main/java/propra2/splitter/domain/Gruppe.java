package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gruppe {
    private Integer id;
    private Person gruender;
    private List<Person> personen
            ;
    private List<Ausgabe> gruppenAusgaben;
    private List<Transaktion> transaktionen = new ArrayList<>();
    Map<Person, Money> nettoBetraege = new HashMap<>();
    private Integer groesse = 0;

    private boolean ausgleich;
    boolean geschlossen = false;

    public Gruppe(Integer id, Person gruender, List<Person> personen) {
        this.id = id;
        this.gruender = gruender;
        this.personen = personen;
    }

    public static Gruppe erstelleGruppe(Integer id, String gruender){
        Person person = new Person(gruender, new ArrayList<>(), new ArrayList<>());
        List<Person> personen = new ArrayList<>();
        personen.add(person);
        return new Gruppe(id, person, personen);
    }

    public void addAusgabeToPerson(String aktivitaet, String name, List<String> personen2, Money kosten){

        geschlossen = true;

        Person zahlungsEmpfaenger = new Person("platzhalter", new ArrayList<>(), new ArrayList<>());
        for(Person person : personen){
            if(person.getName().equals(name)){
                zahlungsEmpfaenger = person;
            }
        }

        // payer/Personen, die ausgelegt bekommen haben und später Geld zurückzahlen müssen
        List<Person> teilnehmer = new ArrayList<>();
        for(Person person: personen){
            for(String personName : personen2) {
                if (person.getName().equals(personName)) {
                    teilnehmer.add(person);
                }
            }
        }

        // Ausgaben in Person, welche Ausgabe getätigt hat, speichern
        zahlungsEmpfaenger.addAusgabe(new Ausgabe(new Aktivitaet(aktivitaet), zahlungsEmpfaenger, teilnehmer, kosten));

        // speichert Schulden der payers
        for(Person person : teilnehmer) {
            if (!person.equals(zahlungsEmpfaenger)) {
                person.addSchulden(new Schulden(person, zahlungsEmpfaenger));
            }
        }
    }

    public void noetigeMinimaleTransaktion(){
        Money[] sumAusgaben = new Money[personen.size()];
        for(int i = 0; i < personen.size(); i++){
            sumAusgaben[i] = Money.of(0, "EUR");
        }

        Money ausgabeSum = Money.of(0, "EUR");
        for (int i = 0; i < personen.size(); i++) {
            ausgabeSum = Money.of(0, "EUR");
            for (int j = 0; j < personen.get(i).getAusgaben().size(); j++){
                ausgabeSum = ausgabeSum.add(personen.get(i).getAusgabe(j).getKosten());
                sumAusgaben[i] = ausgabeSum;
            }
        }

        Money[] sumSchuldenListe = new Money[personen.size()];
        for(int i = 0; i < personen.size(); i++){
            sumSchuldenListe[i] = Money.of(0, "EUR");
        }

        Money schuldenSum = Money.of(0, "EUR");
        for (int i = 0; i < personen.size(); i++) {
            schuldenSum = Money.of(0, "EUR");
            for (int j = 0; j < personen.get(i).getSchuldenListe().size(); j++){
                schuldenSum = schuldenSum.add(personen.get(i).getSchuldenListe().get(j).getBetrag());
                sumSchuldenListe[i] = schuldenSum;
            }
        }


        for(int i = 0; i < personen.size(); i++){
            Money betrag = sumAusgaben[i].subtract(sumSchuldenListe[i]);
            nettoBetraege.put(personen.get(i), betrag);
            personen.get(i).setNettoBetrag(betrag);
        }
        minimaleTransaktionen(nettoBetraege);
    }

    private void minimaleTransaktionen(Map<Person, Schulden> personDebtMap){
        Person personMaxCred = getPersonWithMaxValue(personDebtMap);
        Person personMaxDebt = getPersonWithMinValue(personDebtMap);

        if(personDebtMap.get(personMaxCred).betrag.isEqualTo(Money.of(0, "EUR"))
            && personDebtMap.get(personMaxDebt).betrag.isEqualTo(Money.of(0, "EUR"))){
            return;
        }

        Schulden minSchulden = personSchuldenMinimum(personMaxDebt.getMaxValue(personMaxDebt),personMaxCred.getMaxValue(personMaxCred));
        Person minPerson = minSchulden.zahler;
        Money min = minPerson.getMaxValue(minPerson).betrag;
        personMaxCred.getMaxValue(personMaxCred).betrag = personMaxCred.getMaxValue(personMaxCred).betrag.subtract(min);
        personMaxDebt.getMaxValue(personMaxDebt).betrag = personMaxDebt.getMaxValue(personMaxDebt).betrag.add(min);

        String message = personMaxDebt.name + " has to pay " + personMaxCred.getName() + " an amount of " + min;
        Transaktion notwendigeTransaktion = new Transaktion(message);
        transaktionen.add(notwendigeTransaktion);

        minimaleTransaktionen(personDebtMap);
    }

    public List<Transaktion> getTransaktionen() {
        noetigeMinimaleTransaktion();
        return transaktionen;
    }

    private Schulden personSchuldenMinimum(Schulden d1, Schulden d2){
        return d1.zahlungsEmpfaenger.getMaxValue(d1.zahlungsEmpfaenger).betrag.isLessThan(d2.zahler.getMaxValue(d2.zahler).betrag) ? d1 : d2;
    }

    public Person getPersonWithMaxValue(Map<Person, Schulden> personDebtMap){
        return personDebtMap.entrySet().stream()
                .max((e1,e2) -> e1.getValue().betrag.getNumber().intValue() > e2.getValue().betrag.getNumber().intValue() ? 1 : -1).get().getKey();
    }

    public Person getPersonWithMinValue(Map<Person, Schulden> personDebtMap){
        return personDebtMap.entrySet().stream()
                .min((e1,e2) -> e1.getValue().betrag.getNumber().intValue() > e2.getValue().betrag.getNumber().intValue() ? 1 : -1).get().getKey();
    }

    public void addPerson(String newPerson){
        Person person = new Person(newPerson, new ArrayList<>(), new ArrayList<>());
        personen.add(person);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getCreator() {
        return gruender;
    }

    public void setCreator(Person creator) {
        this.gruender = creator;
    }

    public List<Person> getPersonen() {
        return List.copyOf(personen);
    }

    public void setPersonen(List<Person> personen) {
        this.personen = personen;
    }

    public Integer getGroesse() {
        return groesse;
    }

    public void setGroesse(Integer groesse) {
        this.groesse = groesse;
    }

    public boolean isGeschlossen() {
        return geschlossen;
    }

    public void setGeschlossen(boolean geschlossen) {
        this.geschlossen = geschlossen;
    }

}
