package com.amazon.pages;

import org.apache.logging.log4j.LogManager;

public class LoginPage extends BasePage
{
	public LoginPage()
	{
		log = LogManager.getLogger(LoginPage.class.getName());
		log.info("########### Login page constructor ###############");
	}
	
	
}
