package com.amazon.utils;

import java.util.Arrays;

public class Tes 
{
	public static void main(String[] args)
	{
		System.out.println(reverse(-1534236469));
	}
	
	public static int reverse(int x)
	{
		if(x<=Integer.MAX_VALUE && x>=Integer.MIN_VALUE)
		{
			StringBuffer numStr = new StringBuffer(String.valueOf(x));
			if(x<0)
			{
				String negNum = "-"+numStr.reverse().toString().replace("-", "");
				if(Long.parseLong(negNum)>Integer.MIN_VALUE)
				{
					return Integer.parseInt(negNum);
				}
				else
				{
					return 0;
				}
			}
			else
			{
				String posNum = numStr.reverse().toString();
				if(Long.parseLong(posNum)<Integer.MAX_VALUE)
				{
					return Integer.parseInt(posNum);
				}
				else
				{
					return 0;
				}
			}
		}
		else
		{
			return 0;
		}
	}
}
