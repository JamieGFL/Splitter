package propra2.splitter;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = SplitterApplication.class)
public class ArchTests {

    @ArchTest
    static final ArchRule onion = onionArchitecture()
            .domainModels("propra2.splitter.domain..")
            .domainServices("propra2.splitter.domain..")
            .applicationServices("propra2.splitter.service..")
            .adapter("web", "propra2.splitter.web..");


}
