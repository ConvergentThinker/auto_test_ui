package org.autotestui.utilities;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Sakthivel I
 * @since 01 Sep 2022
 */

@Component
@Slf4j
public class PageAction {

    @Autowired
    @Lazy
    private WebDriver webDriver;

    @Autowired
    @Lazy
    private FluentWait<WebDriver> fluentWait;

    @Value("${default.timeoutInSeconds:30}")
    private int defaultTimeoutInSeconds;


    @Lookup
    public WebDriver getWebDriver() {
        return null;
    }

    private FluentWait<WebDriver> getFluentWait(int durationInSeconds, Class<? extends Throwable>... exceptionsToIgnore) {
        return fluentWait
                .withTimeout(Duration.ofSeconds(durationInSeconds))
                .ignoreAll(Arrays.asList(exceptionsToIgnore));
    }

    public FluentWait<WebDriver> getFluentWait(int durationInSeconds, boolean isNew, Class<? extends Throwable>... exceptionsToIgnore) {
        fluentWait = new FluentWait<WebDriver>(getWebDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoreAll(Arrays.asList(
                        NoSuchElementException.class,
                        StaleElementReferenceException.class)
                );

        return fluentWait
                .withTimeout(Duration.ofSeconds(durationInSeconds))
                .ignoreAll(Arrays.asList(exceptionsToIgnore));
    }

    public PageAction enterText(WebElement webElement, String inputString, int timeoutInSecond) {
        log.debug("[IAF] Entering text '{}' to element '{}'", inputString, webElement.getAccessibleName());
        try {
            fluentWait.withTimeout(Duration.ofSeconds(timeoutInSecond))
                    .ignoring(ElementNotInteractableException.class)
                    .until(ExpectedConditions.elementToBeClickable(webElement))
                    .sendKeys(inputString);
        } catch (TimeoutException toe) {
            log.error("[IAF] Failed to enter text '{}' to element '{}'", inputString, webElement);
            highlightElement(webElement, toe);
            throw toe;
        }
        return this;
    }

    public PageAction enterText(WebElement webElement, String inputString) {
        return enterText(webElement, inputString, defaultTimeoutInSeconds);
    }

    public PageAction clearText(WebElement webElement) {
        try {
            fluentWait.withTimeout(Duration.ofSeconds(defaultTimeoutInSeconds))
                    .ignoring(ElementNotInteractableException.class)
                    .until(ExpectedConditions.elementToBeClickable(webElement))
                    .clear();
            if (!this.getText(webElement).equalsIgnoreCase("")) {
                this.enterText(webElement, Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            }
        } catch (TimeoutException toe) {
            log.error("[IAF] Failed to clear text for element '{}'", webElement);
            highlightElement(webElement, toe);
            throw toe;
        }
        return this;
    }

    public PageAction clearAndEnterText(WebElement webElement, String inputString, int timeoutInSeconds) {
        return this
                .clearText(webElement)
                .enterText(webElement, inputString, timeoutInSeconds);
    }

    public PageAction clearAndEnterText(WebElement webElement, String inputString) {
        return this
                .clearText(webElement)
                .enterText(webElement, inputString);
    }

    public PageAction hitTabButton(WebElement webElement) {
        getFluentWait(10, NoSuchElementException.class, InvalidElementStateException.class)
                .until(ExpectedConditions.visibilityOf(webElement));
        webElement.sendKeys(Keys.TAB);
        return this;
    }

    public String getText(WebElement webElement) {
        getFluentWait(10, NoSuchElementException.class, InvalidElementStateException.class)
                .until(ExpectedConditions.visibilityOf(webElement));
        if (webElement.getTagName().equalsIgnoreCase("input")) {
            return (webElement.getText().equalsIgnoreCase("")) ? webElement.getAttribute("value") : webElement.getText();
        }
        return webElement.getText();
    }

    public PageAction clickFirstElement(List<WebElement> elementList) {
        elementList.stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public PageAction click(WebElement webElement, int timeoutInSecond) {
        try {
            fluentWait.withTimeout(Duration.ofSeconds(timeoutInSecond))
                    .ignoring(ElementNotInteractableException.class)
                    .until(ExpectedConditions.elementToBeClickable(webElement))
                    .click();
        } catch (TimeoutException toe) {
            highlightElement(webElement, toe);
            throw toe;
        }
        return this;
    }

    public PageAction click(WebElement webElement) {
        return click(webElement, defaultTimeoutInSeconds);
    }

    public PageAction superClick(WebElement webElement, int timeoutInSecond) {
        try {
            System.out.println("SuperClick[1] - try normal click");
            fluentWait.withTimeout(Duration.ofSeconds(timeoutInSecond))
                    .ignoring(ElementNotInteractableException.class)
                    .until(ExpectedConditions.elementToBeClickable(webElement))
                    .click();
        } catch (Exception toe) {
            try {
                System.out.println("  - " + toe.getMessage());
                System.out.println("SuperClick[2] - try send ENTER key");
                webElement.sendKeys(Keys.ENTER);
            } catch (Exception e1) {
                try {
                    System.out.println("  - " + e1.getMessage());
                    System.out.println("SuperClick[4] - try with Javascript");
                    executeJS("scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});", webElement)
                            .executeJS("click();", webElement);
                } catch (Exception e3) {
                    System.out.println("  - " + e3.getMessage());
                    highlightElement(webElement, toe);
                    throw e3;
                }
            }
        }
        return this;
    }

    // todo - handle properly? how about by text,value,index?
    public PageAction dropDownSelectByValue(WebElement webElement, String value) {
        getFluentWait(10, NoSuchElementException.class, InvalidElementStateException.class)
                .until(ExpectedConditions.visibilityOf(webElement));
        new Select(webElement).selectByValue(value);
        return this;
    }

    public PageAction dropDownSelectByText(WebElement webElement, String value) {
        getFluentWait(10, NoSuchElementException.class, InvalidElementStateException.class)
                .until(ExpectedConditions.visibilityOf(webElement));
        new Select(webElement).selectByVisibleText(value);
        return this;
    }

    public PageAction dropDownSelectByIdx(WebElement webElement, int idx) {
        getFluentWait(10, NoSuchElementException.class, InvalidElementStateException.class)
                .until(ExpectedConditions.visibilityOf(webElement));
        new Select(webElement).selectByIndex(idx);
        return this;
    }

    public PageAction highlightElement(WebElement webElement, Exception exception) {
        if (exception.getCause() instanceof NoSuchElementException) {
            return this; // no point to highlight not found element, it'll just throw another NoSuchElementException.
        }

        //if element is initially hidden (style="visibility: hidden"), framework will force it to be shown and highlight with dashed-blue
        if (Objects.nonNull(webElement.getAttribute("style"))) {
            if (webElement.getAttribute("style").contains("visibility: hidden")) {
                ((JavascriptExecutor) getWebDriver())
                        .executeScript("arguments[0].setAttribute('style', 'border: 2px dashed blue;');", webElement);
                return this;
            }
        }

        //if element not hidden and available, it'll highlight with dashed-red
        ((JavascriptExecutor) getWebDriver())
                .executeScript("arguments[0].setAttribute('style', 'border: 2px dashed red;');", webElement);
        return this;
    }

    public PageAction executeJS(String command, WebElement webElement) {
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0]." + command, webElement);
        return this;
    }
}
