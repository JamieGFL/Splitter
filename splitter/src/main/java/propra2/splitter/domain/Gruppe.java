package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.*;

public class Gruppe {
    private UUID id;
    private Person gruender;
    private List<Person> personen
            ;
    private List<Ausgabe> gruppenAusgaben;
    private List<Transaktion> transaktionen = new ArrayList<>();
    ArrayList<Person> nettoBetraege = new ArrayList<>();
    private Integer groesse = 0;

    private boolean ausgleich = false;
    boolean geschlossen = false;

    public Gruppe(Person gruender, List<Person> personen) {
        this.id = UUID.randomUUID();
        this.gruender = gruender;
        this.personen = personen;
    }

    public static Gruppe erstelleGruppe(String gruender){
        Person person = new Person(gruender, new ArrayList<>(), new ArrayList<>());
        List<Person> personen = new ArrayList<>();
        personen.add(person);
        return new Gruppe(person, personen);
    }

    public void addPerson(String newPerson){
        Person person = new Person(newPerson, new ArrayList<>(), new ArrayList<>());
        personen.add(person);
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

    public void noetigeTransaktionen(){
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
                schuldenSum = schuldenSum.add(personen.get(i).getSchulden(j).getBetrag());
                sumSchuldenListe[i] = schuldenSum;
            }
        }


        for(int i = 0; i < personen.size(); i++){
            Money betrag = sumAusgaben[i].subtract(sumSchuldenListe[i]);
            personen.get(i).setNettoBetrag(betrag);
            nettoBetraege.add(personen.get(i));
        }
        transaktionen(nettoBetraege);
    }

    private void transaktionen(ArrayList<Person> nettoBetraege){
        //Person mit maximalem Netto-Betrag
        Person personMaxGutschrift = getPersonWithMaxNettoBetrag(nettoBetraege);
        //Person mit minimalem Netto-Betrag
        Person personMaxSchulden = getPersonWithMinNettoBetrag(nettoBetraege);

        //Falls alle Netto-Beträge 0 sind, ist bereits alles ausgeglichen
        for(var entry : nettoBetraege){
            if(entry.getNettoBetrag().isZero()){
                ausgleich = true;
            }
            if(!entry.getNettoBetrag().isZero()){
                ausgleich = false;
                break;
            }
        }
        if(ausgleich && transaktionen.isEmpty()){
            transaktionen.add(new Transaktion());
        }

        // Rekursionsabbruch bei fertigem Ausgleich
        if(personMaxGutschrift.getNettoBetrag().toString().equals("EUR 0.00")
            && personMaxSchulden.getNettoBetrag().toString().equals("EUR 0.00")){
            return;
        }

        // Minimum der NettoBeträge von personMaxSchulden und personMaxGutschrift wird gespeichert
        // personMaxSchulden's NettoBetrag muss für diesen Prozess negiert werden, damit Rechnung später korrekt ausgleicht
        personMaxSchulden.setNettoBetrag(personMaxSchulden.getNettoBetrag().negate());
        List<Person> list = List.of(personMaxSchulden, personMaxGutschrift);
        Person minPerson = getPersonWithMinNettoBetrag(list);
        Money min = minPerson.getNettoBetrag();
        personMaxSchulden.setNettoBetrag(personMaxSchulden.getNettoBetrag().negate());

        // NettoBetraege werden verrechnet > Ausgleich
        personMaxGutschrift.setNettoBetrag(personMaxGutschrift.getNettoBetrag().subtract(min));
        personMaxSchulden.setNettoBetrag(personMaxSchulden.getNettoBetrag().add(min));

        // Bearbeitete Person wird aus der Liste genommen
        if(personMaxGutschrift.getNettoBetrag().isEqualTo(Money.of(0.00, "EUR"))){
            nettoBetraege.remove(personMaxGutschrift);
        }
        else if(personMaxSchulden.getNettoBetrag().isEqualTo(Money.of(0.00, "EUR"))){
            nettoBetraege.remove(personMaxSchulden);
        }

        // Transaktion wird erstellt und gespeichert, Rollen werden vergeben
        Transaktion newTransaktion = new Transaktion(personMaxSchulden, personMaxGutschrift, min);
        transaktionen.add(newTransaktion);

        transaktionen(nettoBetraege);
    }

    public List<Transaktion> getTransaktionen() {
        noetigeTransaktionen();
        return transaktionen;
    }


    private Person getPersonWithMaxNettoBetrag(List<Person> nettoBetraege){
        PersonComparator personComparator = new PersonComparator();
        return Collections.max(nettoBetraege, personComparator);
    }

    private Person getPersonWithMinNettoBetrag(List<Person> nettoBetraege){
        PersonComparator personComparator = new PersonComparator();
        return Collections.min(nettoBetraege, personComparator);
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
