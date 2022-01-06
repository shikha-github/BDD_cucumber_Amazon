package com.amazon.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Utility 
{
	public static List<String> getListData(Object value)
	{
		if(value!=null)
		{
			return new ArrayList<String>(Arrays.asList(value.toString()));
		}
		else
		{
			return new ArrayList<String>(Arrays.asList(""));
		}
	}
	
	public static boolean ifMatchPattern(String pattern, String value)
	{
		if(value!=null)
		{
			boolean flag = value.contains(pattern);
			return flag;  
		}
		else
		{
			return false;
		}
	}
	
	public static String getSubtextBefore(String value, String separator)
	{
		return StringUtils.substringBefore(value, separator);
	}
	
	public static String getSubtextAfter(String value, String separator)
	{
		return StringUtils.substringAfter(value, separator);
	}
	
	public static String getSubtextBetween(String value, String first, String last)
	{
		return StringUtils.substringBetween(value, first, last);
	}
}
