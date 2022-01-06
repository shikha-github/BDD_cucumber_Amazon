package com.amazon.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.*;

import com.amazon.hooks.Hooks;

public class BasePage 
{
	protected  HashMap<String, String> testdata;
	protected  Map<String, List<String>> browserEnv;
	public static Logger log = LogManager.getLogger(BasePage.class.getName());
	protected WebDriver driver;
	
	public BasePage()
	{
		
	}
	
	public void setupConfigurations(HashMap<String, String> data)
	{
		testdata = data;
		browserEnv = Hooks.getBrowserEnv();
		driver = Hooks.getDriverManager();
	}
	
	public void LaunchUrlInBrowser()
	{
		driver.get(browserEnv.get("URL").get(0));
		log.info("########## Browser is launched successfully ############");
	}
}
