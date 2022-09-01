package org.autotestui.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.autotestui.model.BasicFrameworkModel;
import org.autotestui.uibase.base.StepBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class StepHandlingExcel extends StepBase {

    @Autowired
    private List<BasicFrameworkModel> lstBasicFrameworkModel;

    private String currentScenario;
    private BasicFrameworkModel frameworkModel;

    @Given("User load excel data to POJO")
    public void userLoadExcelDataToPOJO() {
        System.out.println(lstBasicFrameworkModel.size());
    }

    @When("User select scenario {string}")
    public void userSelectScenario(String scenarioName) {
        this.currentScenario = scenarioName;
        System.out.println("Data for : " + scenarioName);
        frameworkModel = generalUtils.getData(lstBasicFrameworkModel, scenarioName, BasicFrameworkModel.class);
        System.out.println(" > " + frameworkModel.toString());

    }

    // Learn: Hamcrest hasProperty, equalTo, is, allOf
    @Then("Excel data is mapped correctly with java object")
    public void excelDataIsMappedCorrectlyWithJavaObject() {
        if (currentScenario.contains("TC10")) {
            assertThat(frameworkModel, allOf(
                    hasProperty("strData", equalTo("12345")),
                    hasProperty("intData", equalTo(0)),
                    hasProperty("floatData", equalTo(0.0F)),
                    hasProperty("boolData", is(false)),
                    hasProperty("dateData"),
                    hasProperty("emailData", equalTo("1234")),
                    hasProperty("multiData", hasSize(0))
            ));
        } else {
            assertThat("Object ToString mismatch!", frameworkModel.toString(), equalTo(frameworkModel.getExpectedResult()));
        }
    }
}
