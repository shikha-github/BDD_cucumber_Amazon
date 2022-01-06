package com.amazon.runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
		features = "features",
		glue = {"com.amazon.stepDefinitions", "com.amazon.hooks"} //Path of step definition file
		,plugin= {"pretty", "html:target/cucumber-reports/cucumber-html-report", 
				"json:target/cucmber-reports/CucumberTestReport.json",
				"rerun:target/cucmber-reports/rerun.txt"
				,"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
				//,"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/cucumber-html-report/report.html"
				} //To generate types of reporting
		,tags= {}
		,monochrome=true //To print output on console in readable format
		,dryRun=false //When set true, it will check mapping between feature file and step definition file and will not run any scenario actually
		,strict=true //When dryRun is false and strict is true, it will run all Scenarios and will check if ant step def is not defined in StepDefinition
		)

public class TestRunner
{
private TestNGCucumberRunner testNgCucumberRunner;
	
	@BeforeClass(alwaysRun=true)
	public void setUpClass() {
		testNgCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
	
	@Test(groups = "cucumber scenarios", description = "Runs Cucumber Feature", dataProvider = "scenario")
    public void feature(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
    	testNgCucumberRunner.runScenario(pickleEvent.getPickleEvent());
    }
 
    @DataProvider(parallel=false) //Default thread count is 10
    public Object[][] scenario() {
        return testNgCucumberRunner.provideScenarios();
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
    	testNgCucumberRunner.finish();
    }
}