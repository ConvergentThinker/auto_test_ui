package org.autotestui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "@target/TEST-RESULT/rerun.txt",
        glue = "org.autotestui",
        tags = "@framework1",
        plugin = {
                "pretty",
                "json:target/TEST-RESULT-RERUN/cucumber-result.json",
                "html:target/TEST-RESULT-RERUN/cucumber-result.html",
                "timeline:target/TEST-RESULT-RERUN/cucumber-result-timeline",
                "rerun:target/TEST-RESULT-RERUN/rerun.txt"
        }
)
public class TestRunnerFailed extends AbstractTestNGCucumberTests {
}