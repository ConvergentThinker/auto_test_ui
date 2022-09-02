package org.autotestui.uibase.base;

import io.cucumber.java.*;
import lombok.extern.slf4j.Slf4j;
import org.autotestui.utilities.World;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Slf4j
public class CucumberHook {

    @Autowired
    private AShot screenshotUtils;

    @Autowired
    private ApplicationContext appCtx;

    @Value("${chrome.terminateOnExit}")
    private boolean isTerminate;

    @Value("${screenshot.event}")
    private String screenshotEvent;

    @Value("${screenshot.target}")
    private String screenshotTarget;

    @Value("${screenshot.frequency}")
    private String screenshotFrequency;

    @Before
    public void setup(Scenario scenario) throws IOException {
        List<String> tags = (List<String>) scenario.getSourceTagNames();
        log.trace("[IAF] Scenario '{}' contains tags : {}", scenario.getName(), tags);
        boolean isForceBrowser = tags
                .stream()
                .anyMatch(t -> t.equalsIgnoreCase("@firefox") || t.equalsIgnoreCase("@chrome"));

        World.NoBrowser = scenario.getSourceTagNames()
                .stream()
                .anyMatch(t -> t.equalsIgnoreCase("@nobrowser"));

        if (isForceBrowser) {
            if (tags.contains("@chrome"))
                World.ForceBrowser = "chrome";
            else
                World.ForceBrowser = "firefox";
        } else {
            World.ForceBrowser = "auto";
        }

        if (!screenshotEvent.trim().equalsIgnoreCase("off") && !World.NoBrowser) {
            if (screenshotFrequency.trim().equalsIgnoreCase("all")
                    && (screenshotTarget.trim().equalsIgnoreCase("all")
                    || (screenshotTarget.trim().equalsIgnoreCase("scenario")))) {
                if (screenshotEvent.trim().equalsIgnoreCase("failed") && scenario.isFailed()) {
                    takeScreenshot(scenario, "Before failed Scenario -- ");
                }
                if (screenshotEvent.trim().equalsIgnoreCase("all")) {
                    takeScreenshot(scenario, "Before failed/passed Scenario -- ");
                }
            }
        }
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (!World.NoBrowser || scenario.getSourceTagNames().size() == 0) {
            WebDriver d = appCtx.getBean(WebDriver.class);
            if (isTerminate) {
                log.info("Terminating browser {}", d);
                d.quit();
            }
        }
    }

    @After(order = 2)
    public void afterScenario(Scenario scenario) throws IOException {
        if (!screenshotEvent.trim().equalsIgnoreCase("off") && !World.NoBrowser) {
            if ((screenshotFrequency.trim().equalsIgnoreCase("all")
                    || screenshotFrequency.trim().equalsIgnoreCase("after"))
                    && (screenshotTarget.trim().equalsIgnoreCase("all")
                    || (screenshotTarget.trim().equalsIgnoreCase("scenario")))) {
                if (screenshotEvent.trim().equalsIgnoreCase("failed") && scenario.isFailed()) {
                    takeScreenshot(scenario, "After failed Scenario -- ");
                }
                if (screenshotEvent.trim().equalsIgnoreCase("all")) {
                    takeScreenshot(scenario, "After failed/passed Scenario -- ");
                }
            }
        }
        attachBrowserLog(scenario);
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) throws IOException {
        if (!screenshotEvent.trim().equalsIgnoreCase("off") && !World.NoBrowser) {
            if (screenshotFrequency.trim().equalsIgnoreCase("all")
                    && screenshotTarget.trim().equalsIgnoreCase("all")) {
                if (screenshotEvent.trim().equalsIgnoreCase("failed") && scenario.isFailed()) {
                    takeScreenshot(scenario, "Before failed step -- ");
                }
                if (screenshotEvent.trim().equalsIgnoreCase("all")) {
                    takeScreenshot(scenario, "Before failed/passed step -- ");
                }
            }
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) throws IOException {
        if (!screenshotEvent.trim().equalsIgnoreCase("off") && !World.NoBrowser) {
            if ((screenshotFrequency.trim().equalsIgnoreCase("all")
                    || screenshotFrequency.trim().equalsIgnoreCase("after"))
                    && screenshotTarget.trim().equalsIgnoreCase("all")) {
                if (screenshotEvent.trim().equalsIgnoreCase("failed") && scenario.isFailed()) {
                    takeScreenshot(scenario, "After failed step -- ");
                }
                if (screenshotEvent.trim().equalsIgnoreCase("all")) {
                    takeScreenshot(scenario, "After failed/passed step -- ");
                }
            }
        }
        attachBrowserLog(scenario);
    }

    private void takeScreenshot(Scenario scenario, String logData) throws IOException {
        WebDriver webDriver = appCtx.getBean(WebDriver.class);
        Screenshot ss = screenshotUtils.shootingStrategy(
                ShootingStrategies.viewportPasting(50))
                .takeScreenshot(webDriver);
        scenario.attach(toByteArray(ss.getImage(), "png"), "image/png", scenario.getName());
        log.trace("[IAF] Screenshot {}", logData + scenario.getName());
    }

    private void attachBrowserLog(Scenario scenario) {
        if (scenario.isFailed() && !World.NoBrowser) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebDriver webDriver = appCtx.getBean(WebDriver.class);
            StringBuilder logResult = new StringBuilder();
            for (LogEntry e : webDriver.manage().logs().get(LogType.BROWSER)) {
                logResult.append("[").append(e.getLevel()).append("] -- ").append(e.getMessage());
                logResult.append("\n");
            }
            scenario.attach(logResult.toString(), "text/plain", "Browser Console Log");
        }
    }

    private byte[] toByteArray(BufferedImage bi, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();
    }
}
