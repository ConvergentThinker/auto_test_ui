package org.autotestui.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.autotestui.annotation.Page;
import org.autotestui.pageobject.PageUIRegistration;
import org.autotestui.uibase.base.StepBase;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StepHandlingUIBasic extends StepBase {

    @Page
    private PageUIRegistration registrationPg;

    @Given("User on UI registration form")
    public void launchSite() {
        registrationPg.goTo();
    }

    @When("User select from country {string} and to country {string}")
    public void selectCountry(String from, String to) {
        registrationPg.setCountryFromAndTo(from, to);
    }

    @And("User enter dob as {string}")
    public void enterDob(String dob) {
        registrationPg.setBirthDate(LocalDate.parse(dob));
    }

    @And("User enter name as {string} and {string}")
    public void enterNames(String fn, String ln) {
        registrationPg.setNames(fn, ln);
    }

    @And("User enter contact details as {string} and {string}")
    public void enterContactDetails(String email, String phone) {
        registrationPg.setContactDetails(email, phone);
    }

    @And("User enter the comment {string}")
    public void enterComment(String comment) {
        registrationPg.setComment(comment);
    }

    @And("User submit the registration form")
    public void submit() {
        registrationPg.submit();
    }

    @Then("User should see the confirmation number displayed")
    public void verifyConfirmationNumber() throws InterruptedException {
        assertThat("Registration number is not displayed!", registrationPg.getConfirmationNo().trim(), not(emptyString()));
    }

}
