package propra2.splitter.domain;

import java.util.List;

public class Person{

    String name;
    List<Ausgabe> ausgaben;
    List<Schulden> schuldenListe;

    public Person(String name, List<Ausgabe> ausgaben, List<Schulden> schuldens) {
        this.name = name;
        this.ausgaben = ausgaben;
        this.schuldenListe = schuldens;
    }

    public Schulden getMaxValue(Person person){
        Schulden maxSchulden = schuldenListe.get(0);
        for(Schulden schulden : schuldenListe){
            if(maxSchulden.betrag.isLessThan(schulden.betrag)){
                maxSchulden = schulden;
            }
        }
        return maxSchulden;
//        return person.debts.stream().max((e1,e2) -> e1.amount.getNumber().intValue() > e2.amount.getNumber().intValue() ? 1 : -1).get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ausgabe> getAusgaben() {
        return ausgaben;
    }

    public void setAusgaben(List<Ausgabe> ausgaben) {
        this.ausgaben = ausgaben;
    }

    public List<Schulden> getSchulden() {
        return schuldenListe;
    }

    public void setSchulden(List<Schulden> schuldens) {
        this.schuldenListe = schuldens;
    }

    //    @Override
//    public int compare(Person o1, Person o2) {
//        if(o1.totalExpense.subtract(o2.totalExpense).isLessThan(Money.of(0, "EUR"))){
//            return -1;
//        }
//        else if(o1.totalExpense.subtract(o2.totalExpense).isEqualTo(Money.of(0, "EUR"))){
//            return 0;
//        } else {
//            return 1;
//        }
//    }
}


