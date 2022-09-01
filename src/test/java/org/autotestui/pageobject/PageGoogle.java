package org.autotestui.pageobject;


import org.autotestui.annotation.PageObject;
import org.autotestui.uibase.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@PageObject
public class PageGoogle extends PageBase {
    @FindBy(name = "q")
    private WebElement txtSearch;

    @FindBy(name = "btnK")
    private List<WebElement> btnSearch;

    @FindBy(css = "div.g")
    private List<WebElement> lblSearchResults;

    @Value("${app.url}")
    private String mainURL;

    /* override if required, otherwise it'll go to app.url in application-ABC.properties */
    //    @Override
    //    public void goTo() {
    //        this.webDriver.get("https://google.com");
    //    }

    public void search(String query) {
//        rs.sendKeys(txtSearch , query);
//        rs.click(btnSearch.get(0));
        pageAction.enterText(txtSearch, query)
                .hitTabButton(txtSearch)
                .clickFirstElement(btnSearch);
    }

    public int getResultCount() {
        return lblSearchResults.size();
    }

    public String getTitle() {
        return getCurrentURL();
    }

}
