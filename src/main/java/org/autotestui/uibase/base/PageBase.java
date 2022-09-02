package org.autotestui.uibase.base;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Slf4j
public abstract class PageBase {

    @Autowired
    @Lazy
    protected WebDriver webDriver;

    @PostConstruct
    private void initPageFactory() {
        PageFactory.initElements(this.webDriver, this);
    }

    @PreDestroy
    protected void killBrowser() {
        //webDriver.quit();
    }

    @Lookup
    protected WebDriver getWebDriver() {
        return null;
    }

    @Value("${app.url}")
    protected String mainURL;

    @Value("${spring.profiles.active}")
    protected String currentEnvironment;

    protected String getTitle() {
        return webDriver.getTitle();
    }

    protected String getCurrentURL() {
        return webDriver.getCurrentUrl();
    }

    protected void goTo() {
        this.webDriver.get(mainURL);
    }

    protected String getEnvironment() {
        return currentEnvironment;
    }
}
