package org.autotestui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

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
public class TestRunnerLocal extends AbstractTestNGCucumberTests {
}