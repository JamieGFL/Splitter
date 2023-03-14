package propra2.splitter;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = SplitterApplication.class , importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchTests {

    @ArchTest
    static final ArchRule onion = onionArchitecture()
            .domainModels("propra2.splitter.domain..")
            .domainServices("propra2.splitter.domain..")
            .applicationServices("propra2.splitter.service..")
            .adapter("web", "propra2.splitter.web..");


    @ArchTest
    static final ArchRule rule1 = noFields().should().beAnnotatedWith(Autowired.class).orShould().beAnnotatedWith(Value.class);

    @ArchTest
    static final ArchRule rule2 = classes().that().areAnnotatedWith(Configuration.class).should().resideInAPackage("..config..");

    @ArchTest
    static final ArchRule rule3 = classes().that().areAnnotatedWith(Service.class).should().resideInAPackage("..service..");

    @ArchTest
    static final ArchRule rule4 = classes().that().areAnnotatedWith(Controller.class).should().resideInAPackage("..web..");
}
