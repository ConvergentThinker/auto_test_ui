package org.autotestui.pageobject;


import org.autotestui.annotation.PageObject;
import org.autotestui.uibase.base.PageBase;
import org.autotestui.utilities.PageAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@PageObject
public class PageUIRegistration extends PageBase {

    @FindBy(id = "first_4")
    private WebElement firstName;

    @FindBy(id = "last_4")
    private WebElement lastName;

    @FindBy(id = "input_46")
    private WebElement fromCountry;

    @FindBy(id = "input_47")
    private WebElement toCountry;

    @FindBy(id = "input_24_month")
    private WebElement month;

    @FindBy(id = "input_24_day")
    private WebElement day;

    @FindBy(id = "input_24_year")
    private WebElement year;

    @FindBy(id = "input_6")
    private WebElement email;

    @FindBy(id = "input_27_phone")
    private WebElement phone;

    @FindBy(id = "input_45")
    private WebElement comments;

    @FindBy(id = "submitBtn")
    private WebElement submit;

    @FindBy(id = "requestnumber")
    private WebElement requestNumber;

    @Autowired
    private PageAction pageAction;


    // Sample overriding but selecting specific environment
    public void goTo() {
        if (getEnvironment().trim().contains("uat")) {
            webDriver.get("https://vins-udemy.s3.amazonaws.com/sb/visa/udemy-visa.html");
        } else if (getEnvironment().trim().contains("sit")) {
            webDriver.get("https://sit-vins-udemy.s3.amazonaws.com/sb/visa/udemy-visa.html");
        } else {
            webDriver.get("https://vins-udemy.s3.amazonaws.com/sb/visa/udemy-visa.html");
        }
    }

    public void setNames(String fn, String ln) {
        pageAction
                .enterText(firstName, fn)
                .enterText(lastName, ln);
    }

    public void setCountryFromAndTo(String from, String to) {
        pageAction.dropDownSelectByText(fromCountry, from)
                .dropDownSelectByText(toCountry, to);
    }

    public void setBirthDate(LocalDate localDate) {
        pageAction.dropDownSelectByText(year, String.valueOf(localDate.getYear()))
                .dropDownSelectByValue(month, localDate.getMonth().toString())
                .dropDownSelectByText(day, String.valueOf(localDate.getDayOfMonth()));
    }

    public void setContactDetails(String email, String phone) {
        pageAction.enterText(this.email, email)
                .enterText(this.phone, phone);
    }

    public void setComment(String comment) {
        comments.sendKeys(comment);
    }

    public void submit() {
        pageAction.click(submit);
    }

    public String getConfirmationNo() throws InterruptedException {
        return pageAction.getText(requestNumber);
    }
}