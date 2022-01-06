package com.amazon.stepDefinitions;

import java.util.HashMap;
import java.util.List;

import com.amazon.hooks.Hooks;
import com.amazon.pages.BasePage;
import com.amazon.pages.LoginPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonSteps 
{
	private static List<HashMap<String, String>> testData;
	
	@When("test data file {string} and sheet name {string} is obtained")
	public void test_data_file_and_sheet_name_is_obtained(String filename, String sheet) {
		Hooks.setExcelFileName(filename);
	    Hooks.setExcelSheetName(sheet);
	}
	
	@Then("setup testdata for scenario being run")
	public void setup_testdata_for_scenario_being_run() 
	{
		testData = Hooks.getExcelData();
	}
	
	@Given("user launches the application in browser for data {string}")
	public void user_launches_the_application_in_browser_for_data(String row_index) {
	    BasePage base = new BasePage();
		base.setupConfigurations(testData.get(Integer.parseInt(row_index)));
	    base.LaunchUrlInBrowser();
	}
	
	@Given("user launches the application in browser")
	public void user_launches_the_application_in_browser() {
	    BasePage base = new BasePage();
		base.setupConfigurations(testData.get(0));
	    base.LaunchUrlInBrowser();
	}
	
	@And("user enters the email address")
	public void user_enters_the_email_address() {
	    LoginPage login = new LoginPage();
	}

	@And("user clicks Continue button")
	public void user_clicks_Continue_button() {
		
	}

	@And("user enters the password")
	public void user_enters_the_password() {
	    
	}

	@Then("user clicks Login button")
	public void user_clicks_Login_button() {
	    
	}
}
