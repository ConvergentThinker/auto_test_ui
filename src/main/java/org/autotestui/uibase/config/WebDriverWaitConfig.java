package org.autotestui.uibase.config;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import java.time.Duration;
import java.util.Arrays;


/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */


@Configuration
@Lazy
public class WebDriverWaitConfig {

    @Bean
    public FluentWait<WebDriver> getFluentWait() {
        return new FluentWait<>(getWebdriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoreAll(Arrays.asList(
                        NoSuchElementException.class,
                        StaleElementReferenceException.class)
                );
    }
    @Lookup
    public WebDriver getWebdriver(){
        return null;
    }
}
