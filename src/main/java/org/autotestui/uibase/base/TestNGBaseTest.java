package org.autotestui.uibase.base;

import lombok.extern.slf4j.Slf4j;
import org.autotestui.utilities.World;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Slf4j
@SpringBootTest
public class TestNGBaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ApplicationContext appCtx;

    @Value("${chrome.terminateOnExit}")
    private boolean isTerminate;

    @BeforeGroups(groups = "nobrowser")
    public void setupTestNG() {
        System.out.println(":: TestNG BeforeGroup nobrowser ::");
        World.NoBrowser = true;
    }

//    @BeforeTest
//    public void beforeTestAll() {
//        System.out.println(":: TestNG BeforeTest - ALL ::");
//        World.ForceBrowser = "auto";
//    }

    @AfterTest
    public void tearDown() {
        System.out.println(":: TestNG AfterTest - ALL ::");
        WebDriver d = appCtx.getBean(WebDriver.class);
        if (isTerminate && !World.NoBrowser) {
            log.info("Terminating browser {}", d);
            d.quit();
        }
    }
}
