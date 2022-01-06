package com.amazon.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.amazon.browserConfiguration.DriverManager;
import com.amazon.browserConfiguration.DriverManagerFactory;
import com.amazon.filereader.BrowserEnvironmentReader;
import com.amazon.filereader.ExcelDataReader;
import com.amazon.utils.Utility;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	private static Map<String, List<String>> browserEnvMap = new HashMap<String, List<String>>();
	private static DriverManager driver;
	private static String sheetName;
	private static String fileName;
	
	public static Map<String, List<String>> getBrowserEnv()
	{
		return browserEnvMap;
	}
	
	public static WebDriver getDriverManager()
	{
		return driver.getDriver();
	}
	
	public static void setExcelSheetName(String sheet)
	{
		sheetName=sheet;
	}
	
	public static void setExcelFileName(String file)
	{
		fileName=file;
	}
	
	public static List<HashMap<String, String>> getExcelData()
	{	
		List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		if(getBrowserEnv().get("TestDataFolderPath").get(0)!="")
		{
			String filePath = System.getProperty("user.dir")+getBrowserEnv().get("TestDataFolderPath").get(0)+"\\"+fileName;
			//sheetName = getBrowserEnv().get("SheetName").get(0);
			String testname = getBrowserEnv().get("Testcase").get(0);
			dataList = new ExcelDataReader(filePath, sheetName, testname).getData();
		}
		else
		{
			return null;
		}
		return dataList;
	}
	
	@Before("@EnvironmentSetup")
	public void BrowserEnvironmentSetup(Scenario scenario) {
		browserEnvMap.putAll(new BrowserEnvironmentReader().getBrowserEnvironment());
		System.out.println("Browser Environment: "+browserEnvMap);
		driver = DriverManagerFactory.getManager(browserEnvMap.get("BrowserName").get(0));
		driver.getService();
	}

	@Before
	public void BeforeScenario(Scenario scenario) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		browserEnvMap.put("Testcase", Utility.getListData(scenario.getName()));
		//System.out.println("Browser Env from BeforeScenario: "+browserEnvMap);
	}
	
	@After("@Teardown")
	public void SuiteTeardown()
	{
		driver.quitDriver();
	}
	
	@After
	public void ScenarioTeardown(Scenario scenario)
	{
		if(scenario.isFailed())
		{
			
		}
		driver.closeDriver();
	}
	 
}