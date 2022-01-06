@EnvironmentSetup @Teardown
Feature: This feature has scenarios related to Login functionality

Background: Test data setup
When test data file 'Book1.xlsx' and sheet name 'Sheet1' is obtained
Then setup testdata for scenario being run

Scenario Outline: Verify Login Page is displayed (multiple data scenario)
Given user launches the application in browser for data "<row_index>"
When user enters the email address
And user clicks Continue button
And user enters the password
Then user clicks Login button
Examples:
|row_index|
|0|

Scenario: Verify Login Page is displayed
Given user launches the application in browser