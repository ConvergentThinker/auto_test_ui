package org.autotestui.steps;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Value;

public class StepHandlingSpringProperties {

    @Value("${test.currentprop}")
    String currentProps;

    @Value("${test.main}")
    String mainProps;

    @Value("${test.spring.prof}")
    String springProf;

    @Value("${test.spring.thread}")
    String springThread;

    @Value("${app.url}")
    String appURL;

    @Value("${grid.hub.url}")
    String gridHub;

    @Value("${grid.hub.port}")
    int gridPort;

    @Value("${run.method}")
    String runMethod;

    @Value("${browser}")
    String browser;

    @Given("Test application started with profile")
    public void testApplicationStartedWithProfile() {
        System.out.println("::: [FRAMEWORK] - CURRENT PROPERTIES = " + currentProps);
        System.out.println("::: [FRAMEWORK] - %SPRING.PROF%      = " + springProf);
        System.out.println("::: [FRAMEWORK] - %PARALLELCOUNT%    = " + springThread);
        System.out.println("::: [FRAMEWORK] - MAIN PROPERTIES    = " + mainProps);
        System.out.println("::: [FRAMEWORK] - APP URL            = " + appURL);
        System.out.println("::: [FRAMEWORK] - BROWSER            = " + browser);
        System.out.println("::: [FRAMEWORK] - RUN METHOD         = " + runMethod);
        System.out.println("::: [FRAMEWORK] - GRID HUB URL       = " + gridHub);
        System.out.println("::: [FRAMEWORK] - GRID HUB PORT      = " + gridPort);

    }
}
