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
        System.out.println(" > Excel Row : " + frameworkModel.toString());
    }


    @Then("Read Excel data is mapped  with java object in Model BasicFrameworkModel")
    public void readExcelDataIsMappedWithJavaObjectInModelBasicFrameworkModel() {

        System.out.println("Data from Excel : " + frameworkModel.getScenario());
        System.out.println("Data from Excel : " + frameworkModel.getIntData());
        System.out.println("Data from Excel : " + frameworkModel.getFloatData());

    }
}
