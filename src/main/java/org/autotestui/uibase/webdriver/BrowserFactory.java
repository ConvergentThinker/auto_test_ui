package org.autotestui.uibase.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.autotestui.uibase.webdriver.browserconfig.ChromeConfig;
import org.autotestui.utilities.World;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Service
@Slf4j
public class BrowserFactory {

    @Value("${browser}")
    private String browserType;

    @Value("${run.method}")
    private String runMethod;

    @Value("${grid.hub.url}")
    private String hubURL;

    @Value("${grid.hub.port}")
    private int hubPort;

    @Value("${chromedriver.location}")
    private String chromeDriverLocation;

    @Autowired
    private Environment env;

    @Autowired
    private ChromeConfig chromeConfig;

    private String actualBrowser;

    public WebDriver getWebDriver() {


        switch (runMethod.toUpperCase().trim()) {

            case "LOCAL":
                actualBrowser = browserType;
                if (!World.ForceBrowser.equalsIgnoreCase("auto")) {
                    actualBrowser = World.ForceBrowser;
                }
                if ("FIREFOX".equalsIgnoreCase(actualBrowser)) {
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                } else if ("CHROME".equalsIgnoreCase(actualBrowser)) {
                    System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(getClass().getClassLoader().getResource(chromeDriverLocation)).getFile());
                    ChromeOptions chromeOptions = new ChromeOptions();
                    // -- Chrome Arguments
                    if (env.getProperty("chrome.args.enabled").equalsIgnoreCase("true")) {
                        String[] args = env.getProperty("chrome.args").split(" ");
                        chromeOptions.addArguments(args);
                    }

                    // -- Chrome Preferences
                    HashMap<String, Object> chromePrefs = new HashMap<>();
                    Map<String, String> preferences = Arrays.stream(env.getProperty("chrome.prefs").split(" "))
                            .collect(Collectors
                                    .toMap(s -> s.split("=")[0], s -> s.split("=")[1])
                            );

                    preferences.entrySet().stream()
                            .filter(map -> !(map.getValue().equalsIgnoreCase("default") || map.getValue().equalsIgnoreCase("0")))
                            .forEach(opt -> {
                                switch (opt.getValue().toLowerCase()) {
                                    case "1":
                                    case "2":
                                        chromePrefs.put(opt.getKey(), Integer.parseInt(opt.getValue()));
                                        break;
                                    case "true":
                                    case "false":
                                        chromePrefs.put(opt.getKey(), Boolean.parseBoolean(opt.getValue()));
                                        break;
                                    default:
                                        chromePrefs.put(opt.getKey(), opt.getValue());
                                        break;
                                }
                            });

                    if (env.getProperty("chrome.prefs.enabled").equalsIgnoreCase("true")) {
                        chromeOptions.setExperimentalOption("prefs", chromePrefs);
                    }

                    return new ChromeDriver(chromeOptions);
                } else {
                    return new ChromeDriver();
                }
            case "REMOTE":

                actualBrowser = browserType;
                if (!World.ForceBrowser.equalsIgnoreCase("auto")) {
                    System.out.println("::: Following force which is = " + World.ForceBrowser);
                    actualBrowser = World.ForceBrowser;
                } else {
                    System.out.println("::: is AUTO hence using... " + actualBrowser);
                }

                if ("FIREFOX".equalsIgnoreCase(actualBrowser)) {
                    RemoteWebDriver remoteWebDriver = null;
                    FirefoxOptions opts = new FirefoxOptions();
                    opts.setHeadless(true);
                    try {
                        remoteWebDriver = new RemoteWebDriver(new URL(hubURL), opts);
                        remoteWebDriver.setFileDetector(new LocalFileDetector());
                    } catch (MalformedURLException ex) {
                        log.error(" Failed to establish Remote WebDriver with URL '{}'. Hint: Invalid URL format!", this.hubURL, ex);
                    }
                    return remoteWebDriver;
                } else if ("CHROME".equalsIgnoreCase(actualBrowser)) {
                    RemoteWebDriver remoteWebDriver = null;
                    ChromeOptions opts = new ChromeOptions();
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setCapability(ChromeOptions.CAPABILITY, opts);
                    caps.setPlatform(Platform.ANY);
                    caps.setBrowserName("chrome");
                    caps.setCapability("handlesAlerts", true);
                    caps.setCapability("cssSelectorsEnabled", true);
                    caps.setCapability("network", true); // To enable network logs
                    caps.setCapability("visual", true); // To enable step by step screenshot
                    caps.setCapability("video", true); // To enable video recording
                    caps.setCapability("console", true); // To capture console logs
                    opts.merge(caps);
                    opts.setHeadless(true);
                    try {
                        remoteWebDriver = new RemoteWebDriver(new URL(hubURL), opts);
                        remoteWebDriver.setFileDetector(new LocalFileDetector());
                    } catch (MalformedURLException ex) {
                        log.error(" Failed to establish Remote WebDriver with URL '{}'. Hint: Invalid URL format!", this.hubURL, ex);
                    }
                    return remoteWebDriver;
                } else {
                    return new ChromeDriver();
                }
            default:
                log.warn("Unable to find run method '" + runMethod + "'. Reverted - Run test in LOCAL");
                break;
        }
        return null;
    }

    public RemoteWebDriver getRemoteWebDriver() {
        return null;
    }

    private ChromeOptions getChromeOptions() {
        return null;
    }
}


