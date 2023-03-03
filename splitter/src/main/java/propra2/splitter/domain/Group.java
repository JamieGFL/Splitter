package propra2.splitter.domain;

import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private Integer id;
    private Person creator;
    private List<Person> people;
    private List<Expense> groupExpenses;
    private List<Transaction> transactions = new ArrayList<>();
    Map<Person,Debt> personDebtMap = new HashMap<>();
    private Integer size = 0;
    boolean closed = false;

    public Group(Integer id, Person creator, List<Person> people) {
        this.id = id;
        this.creator = creator;
        this.people = people;
    }

    public static Group createGroup(Integer id, String creator){
        Person person = new Person(creator, new ArrayList<>(), new ArrayList<>());
        List<Person> people = new ArrayList<>();
        people.add(person);
        return new Group(id, person, people);
    }

    public void addExpenseToPerson(String activity, String name, List<String> people2, Money cost){
        Person payee = null;
        for(Person person : people){
            if(person.name.equals(name)){
                payee = person;
            }
        }

        // payer/Personen, die ausgelegt bekommen haben und später Geld zurückzahlen müssen
        List<Person> participants = new ArrayList<>();
        for(Person person:people){
            for(String personName : people2) {
                if (person.name.equals(personName)) {
                    participants.add(person);
                }
            }
        }

        // Ausgaben in Person, welche Ausgabe getätigt hat, speichern
        payee.expenses.add(new Expense(new Activity(activity), payee, participants, cost));

        // speichert Schulden der payers
        for(Person participant : participants) {
            participant.debts.add(new Debt(participant, payee));
        }

    }


    public void requiredMinimalTransactions(){
        for(int p = 0; p < people.size(); p++){
            for(int i = 0; i<people.get(p).getDebts().size(); i++){
                personDebtMap.put(people.get(p),people.get(p).debts.get(i));
            }
        }
        minimalTransactions(personDebtMap);
    }

    private void minimalTransactions(Map<Person,Debt> personDebtMap){
        Person personMaxCred = getPersonWithMaxValue(personDebtMap);
        Person personMaxDebt = getPersonWithMinValue(personDebtMap);

        if(personDebtMap.get(personMaxCred).amount.isEqualTo(Money.of(0, "EUR"))
            && personDebtMap.get(personMaxDebt).amount.isEqualTo(Money.of(0, "EUR"))){
            return;
        }

        Debt minDebt = personDebtMinimum(personMaxDebt.getMaxValue(personMaxDebt),personMaxCred.getMaxValue(personMaxCred));
        Person minPerson = minDebt.payer;
        Money min = minPerson.getMaxValue(minPerson).amount;
        personMaxCred.getMaxValue(personMaxCred).amount = personMaxCred.getMaxValue(personMaxCred).amount.subtract(min);
        personMaxDebt.getMaxValue(personMaxDebt).amount = personMaxDebt.getMaxValue(personMaxDebt).amount.add(min);

        String message = personMaxDebt.name + " has to pay " + personMaxCred.getName() + " an amount of " + min;
        Transaction minimalRequiredTransaction = new Transaction(message);
        transactions.add(minimalRequiredTransaction);

        minimalTransactions(personDebtMap);
    }

    public List<Transaction> getTransactions() {
        requiredMinimalTransactions();
        return transactions;
    }

    private Debt personDebtMinimum(Debt d1, Debt d2){
        return d1.payee.getMaxValue(d1.payee).amount.isLessThan(d2.payer.getMaxValue(d2.payer).amount) ? d1 : d2;
    }

    public Person getPersonWithMaxValue(Map<Person,Debt> personDebtMap){
        return personDebtMap.entrySet().stream()
                .max((e1,e2) -> e1.getValue().amount.getNumber().intValue() > e2.getValue().amount.getNumber().intValue() ? 1 : -1).get().getKey();
    }

    public Person getPersonWithMinValue(Map<Person,Debt> personDebtMap){
        return personDebtMap.entrySet().stream()
                .min((e1,e2) -> e1.getValue().amount.getNumber().intValue() > e2.getValue().amount.getNumber().intValue() ? 1 : -1).get().getKey();
    }

    public void addPerson(String newPerson){
        Person person = new Person(newPerson, new ArrayList<>(), new ArrayList<>());
        people.add(person);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public List<Person> getPeople() {
        return List.copyOf(people);
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}
