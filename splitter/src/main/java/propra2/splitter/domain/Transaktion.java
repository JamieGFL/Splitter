package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.Objects;

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
        this.transaktionsNachricht = "Es sind keine Ausgleichszahlungen notwendig.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaktion)) return false;
        Transaktion that = (Transaktion) o;
        return Objects.equals(getPerson1(), that.getPerson1()) && Objects.equals(getPerson2(), that.getPerson2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson1(), getPerson2());
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public String getTransaktionsNachricht() {
        return transaktionsNachricht;
    }
}
