package com.amazon.filereader;

import java.io.File;

public class FilePathFinder 
{
	private String filePath;
	
	public FilePathFinder(String filename)
	{
		SearchFilePath(filename, new File(System.getProperty("user.dir")));
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	private void SearchFilePath(String filename, File file)
	{
		try
		{
			File[] fileList = file.listFiles();
			if(fileList!=null)
			{
				for(File fil : fileList)
				{
					if(fil.isDirectory())
					{
						SearchFilePath(filename, fil);
					}
					else if(filename.equalsIgnoreCase(fil.getName()))
					{
						filePath = fil.getPath();
						break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		System.out.println(new FilePathFinder("browserconfiguration.xml").getFilePath());
	}
}
