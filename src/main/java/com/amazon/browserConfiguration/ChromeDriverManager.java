package com.amazon.browserConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.amazon.hooks.Hooks;

public class ChromeDriverManager extends DriverManager 
{
	private ChromeDriverService chService;
	
	@Override
	protected void startService() 
	{
		try
		{
			if(chService==null)
			{
				/*System.setProperty("webdriver.chrome.driver", "D:\\NK\\API\\APIProjects\\RestAssuredCucumber\\src\\main\\java\\com\\restassured\\driverexecutables\\chromedriver.exe");
				ChromeDriver driver = new ChromeDriver();*/
				chService = new ChromeDriverService
							.Builder() //Hooks.getBrowserEnv().get("BrowserPath").get(0)
							.usingDriverExecutable(new File(System.getProperty("user.dir")+Hooks.getBrowserEnv().get("BrowserPath").get(0)))
							.usingAnyFreePort()
							.build();
				chService.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void stopService() 
	{
		if (null != chService && chService.isRunning())
            chService.stop();
        	System.err.println("#### ChromeDriver Service is stopped ####");
	}

	@Override
	protected void createDriver() {
		try
		{
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions option = new ChromeOptions();
			option.addArguments("start-maximized", "disable-popup-blocking", "disable-infobars", "version");
			option.setExperimentalOption("useAutomationExtension", false);
			option.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, option);
			//Add provision to include extension if needed
			driver = new ChromeDriver(chService, capabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}