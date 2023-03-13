package propra2.splitter.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import propra2.splitter.domain.Gruppe;
import propra2.splitter.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RestGruppenServiceTests {

    @Test
    @DisplayName("Service kann Gruppen hinzufügen")
    void test_01(){
        RestGruppenService service = new RestGruppenService();

        service.addRestGruppe(new GruppeEntity("Reisegruppe", List.of("MaxHub", "GitLisa")));

        assertThat(service.getRestGruppen().contains(new GruppeEntity("Reisegruppe", List.of("MaxHub", "GitLisa"))));
    }

    @Test
    @DisplayName("Service kann mehrere Gruppen hinzufügen")
    void test_02(){
        RestGruppenService service = new RestGruppenService();

        service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));
        service.addRestGruppe(new GruppeEntity("Reisegruppe2", List.of("GitAndreas", "GitLisa")));
        service.addRestGruppe(new GruppeEntity("Reisegruppe3", List.of("GitAndreas", "MaxHub")));

        assertThat(service.getRestGruppen()).containsExactlyInAnyOrder(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")),
                new GruppeEntity("Reisegruppe2", List.of("GitAndreas", "GitLisa")), new GruppeEntity("Reisegruppe3", List.of("GitAndreas", "MaxHub")));
    }

    @Test
    @DisplayName("Service mapped GruppenEntity korrekt zu Gruppe")
    void test_03(){
        RestGruppenService service = new RestGruppenService();

        UUID id = service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));

        assertThat(service.getSingleGruppe(id).getGruppenName()).isEqualTo("Reisegruppe1");
        assertThat(service.getSingleGruppe(id).getPersonen().stream().map(Person::getName).toList()).isEqualTo(List.of("MaxHub", "GitLisa"));
    }

    @Test
    @DisplayName("Service gibt Gruppeninformationen korrekt zurück")
    void test_04(){
        RestGruppenService service = new RestGruppenService();

        UUID id = service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));

        assertThat(service.getGruppeInformationEntity(id.toString()).name()).isEqualTo("Reisegruppe1");
        assertThat(service.getGruppeInformationEntity(id.toString()).personen()).isEqualTo(List.of("MaxHub", "GitLisa"));
        assertThat(service.getGruppeInformationEntity(id.toString()).geschlossen()).isFalse();
        assertThat(service.getGruppeInformationEntity(id.toString()).ausgaben()).isEqualTo(new ArrayList<>());
    }

    @Test
    @DisplayName("Service kann Ausgaben hinzufügen")
    void test_05(){
        RestGruppenService service = new RestGruppenService();

        UUID id = service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));
        AusgabeEntity ausgabe = new AusgabeEntity("Pizza", "MaxHub", List.of("MaxHub", "GitLisa"), 1000);
        service.addRestAusgabenToGruppe(id.toString(), ausgabe);

        assertThat(service.getGruppeInformationEntity(id.toString()).ausgaben()).containsExactly(ausgabe);
    }

    @Test
    @DisplayName("Service kann Gruppe schließen")
    void test_06(){
        RestGruppenService service = new RestGruppenService();

        UUID id = service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));
        service.setRestGruppeGeschlossen(id.toString());

        assertThat(service.getGruppeInformationEntity(id.toString()).geschlossen()).isTrue();
    }

    @Test
    @DisplayName("Service kann Gruppen einer Person zuordnen")
    void test_07(){
        RestGruppenService service = new RestGruppenService();

        service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));
        service.addRestGruppe(new GruppeEntity("Reisegruppe2", List.of("MaxHub", "GitAndreas")));
        service.addRestGruppe(new GruppeEntity("Reisegruppe3", List.of("GitLisa", "GitAndreas")));

        assertThat(service.personRestMatch("MaxHub")).containsExactlyInAnyOrder(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")),
                new GruppeEntity("Reisegruppe2", List.of("MaxHub", "GitAndreas")));
    }

    @Test
    @DisplayName("Service legt Transaktionen an")
    void test_08(){
        RestGruppenService service = new RestGruppenService();

        UUID id = service.addRestGruppe(new GruppeEntity("Reisegruppe1", List.of("MaxHub", "GitLisa")));
        service.addRestAusgabenToGruppe(id.toString(), new AusgabeEntity("Pizza", "MaxHub", List.of("MaxHub", "GitLisa"), 1000));

        assertThat(service.getRestTransaktionen(id.toString())).containsExactly(new TransaktionEntity("GitLisa", "MaxHub", 500));
    }



}
