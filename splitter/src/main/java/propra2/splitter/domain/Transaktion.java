package propra2.splitter.domain;

import org.javamoney.moneta.Money;

public class Transaktion {
    private Person person1;
    private Person person2;
    private Money nettoBetrag;
    String transaktionsNachricht;

    public Transaktion(Person person1, Person person2, Money nettoBetrag) {
        this.person1 = person1;
        this.person2 = person2;
        this.nettoBetrag = nettoBetrag.abs();
        this.transaktionsNachricht = person1.getName() +" muss " + nettoBetrag + " an " +person2.getName() + " zahlen";
    }

    public Transaktion(){
        this.transaktionsNachricht = "Es sind keine Ausgleichszahlungen notwendig";
    }

    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }

    public Money getNettoBetrag() {
        return nettoBetrag;
    }

    public void setNettoBetrag(Money nettoBetrag) {
        this.nettoBetrag = nettoBetrag;
    }

    public String getTransaktionsNachricht() {
        return transaktionsNachricht;
    }

    public void setTransaktionsNachricht(String transaktionsNachricht) {
        this.transaktionsNachricht = transaktionsNachricht;
    }
}
