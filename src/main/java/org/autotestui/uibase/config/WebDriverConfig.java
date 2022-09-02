package org.autotestui.uibase.config;

import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.autotestui.exceptions.NoBrowserConfigurationException;
import org.autotestui.uibase.webdriver.BrowserFactory;
import org.autotestui.utilities.World;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Spring configuration class that returns WebDriver instance upon request.
 * <p>WebDriver instance is instantiated by {@link BrowserFactory}</p>
 *
 * @author Sakthivel.I
 * @since 01 Sep 2022
 */

@Lazy
@Configuration
@Slf4j
public class WebDriverConfig {

    @Autowired
    private BrowserFactory browserFactory;

    @Bean
    @Scope("BROWSER-SCOPE")
    @ScenarioScope
    public WebDriver getWebDriver() {
        if (World.NoBrowser) {
            throw new NoBrowserConfigurationException("WebDriver is requested from test that is tagged with @nobrowser, please check your feature file");
        }
        WebDriver webDriver = browserFactory.getWebDriver();
        log.info("[IAF] Spin up browser '{}'" , webDriver);
        return webDriver;
    }
}


