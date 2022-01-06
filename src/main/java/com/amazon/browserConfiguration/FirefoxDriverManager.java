package com.amazon.browserConfiguration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxDriverManager extends DriverManager
{
	private GeckoDriverService  fiService;

	@Override
	protected void startService() 
	{
		try
		{
			if(fiService==null)
			{
				fiService = new GeckoDriverService.Builder()
							.usingDriverExecutable(new File(System.getProperty("user.dir")+"//src//main//java//driverExecutables//chromedriver.exe"))
							.usingAnyFreePort()
							.build();
				fiService.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void stopService() {
		if(fiService!=null && fiService.isRunning())
		{
			fiService.stop();
        	System.err.println("#### GeckoDriver Service is stopped ####");
		}
	}

	@Override
	protected void createDriver() 
	{
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		FirefoxOptions option = new FirefoxOptions();
		option.setAcceptInsecureCerts(true);
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, option);
		driver = new FirefoxDriver(fiService, capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("");
	}
}
