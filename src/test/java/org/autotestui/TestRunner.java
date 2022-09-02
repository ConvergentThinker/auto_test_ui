package org.autotestui;
import io.cucumber.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.function.Predicate;

@CucumberOptions(
        features = "classpath:features",
        glue = "org.autotestui",
        tags = "@Framework",
        plugin = {
                "pretty",
                "json:target/TEST-RESULT/cucumber-result.json",
                "html:target/TEST-RESULT/cucumber-result.html",
                "timeline:target/TEST-RESULT/cucumber-result-timeline",
                "rerun:target/TEST-RESULT/rerun.txt"
        }
)
public class TestRunner {

    private TestNGCucumberRunner testNGCucumberRunner;
    private static final Predicate<Pickle> isSerial = p ->
            p.getTags().stream().anyMatch(p1 -> p1.equalsIgnoreCase("@serial") || p1.equalsIgnoreCase("@chrome") || p1.equalsIgnoreCase("@firefox"));
    @BeforeClass(
            alwaysRun = true
    )
    public void setUpClass() {
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Run Parallel", dataProvider = "parallelScenarios")
    public void runParallelScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @Test(groups = "cucumber", description = "Run Serial", dataProvider = "serialScenarios")
    public void runSerialScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] parallelScenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return filter(testNGCucumberRunner.provideScenarios(), isSerial.negate());
    }

    @DataProvider
    public Object[][] serialScenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return filter(testNGCucumberRunner.provideScenarios(), isSerial);
    }

    private Object[][] filter(Object[][] scenarios, Predicate<Pickle> accept) {
        return Arrays.stream(scenarios).filter(objects -> {
            PickleWrapper candidate = (PickleWrapper) objects[0];
            return accept.test(candidate.getPickle());
        }).toArray(Object[][]::new);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }
}