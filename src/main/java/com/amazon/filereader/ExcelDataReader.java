package com.amazon.filereader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader 
{
	private String filePath;
	private String wrkSheetName;
	private String testname;
	public int key=0;
	
	public ExcelDataReader(String excelPath, String excelSheetName, String testname)
	{
		this.filePath = excelPath;
		this.wrkSheetName = excelSheetName;
		this.testname = testname;
	}
	
	public List<Integer> countTestData()
	{
		int testcount=0;
		List<Integer> rowIndex= new ArrayList<Integer>();
		XSSFWorkbook workbook=null;
		FileInputStream file = null;
		try
		{
			file = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(file);
			int sheetNo = workbook.getNumberOfSheets();
			for(int i=0; i<sheetNo; i++)
			{
				if(workbook.getSheetName(i).equalsIgnoreCase(wrkSheetName))
				{
					XSSFSheet sheetName = workbook.getSheetAt(i);
					Iterator<Row> testrows = sheetName.iterator();
					while(testrows.hasNext())
					{
						Row r = testrows.next();
						Iterator<Cell> cellN = r.cellIterator();
						while(cellN.hasNext())
						{
							try
							{
								Cell cellValue = cellN.next();
								if(cellValue.getCellTypeEnum()==CellType.STRING)
								{
									if(cellValue.getStringCellValue().equals(testname))
									{
										rowIndex.add(r.getRowNum());
										//System.out.println(cellValue.getStringCellValue());
										testcount++;
									}
								}
								if(testcount>=2)
								{
									break;
								}
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
					//System.out.println(rowIndex);
					//System.out.println(rowNum-testcount);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				workbook.close();
				file.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rowIndex;
	}
	
	public List<HashMap<String, String>> getData()
	{	
		List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();
		List<Integer> noOfRowsToRead = countTestData();
		HashMap<String, String> data = null;
		ArrayList<String> ColNames = new ArrayList<String>();
		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int sheetNo = workbook.getNumberOfSheets();
		for(int i=0; i<sheetNo; i++)
		{
			if(workbook.getSheetName(i).equalsIgnoreCase(wrkSheetName))
			{
				XSSFSheet sheetName = workbook.getSheetAt(i);
				//Identifying testcases column
				Iterator<Row> rows = sheetName.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cell = firstRow.cellIterator();
				int columnCount=0;
				int columnNo = 0;
				while(cell.hasNext())
				{
					Cell value = cell.next();
					if(value.getStringCellValue().equalsIgnoreCase("Testcase"))
					{
						columnNo=columnCount;
					}
					//To get Column names in arraylist
					if(value.getCellTypeEnum()==CellType.STRING)
					{
						ColNames.add(columnCount, value.getStringCellValue());
					}
					else if(value.getCellTypeEnum()==CellType.NUMERIC)
					{
						String cellData = NumberToTextConverter.toText(value.getNumericCellValue());
						ColNames.add(columnCount, cellData);
					}
					columnCount++;
				}
				
				if(noOfRowsToRead.size()>1)
				{
					for(int j=noOfRowsToRead.get(0); j<=noOfRowsToRead.get(1); j++)
					{
						data =  new HashMap<String, String>();
						Row row = sheetName.getRow(j);
						Iterator<Cell> cellN = row.cellIterator();
						Iterator<String> colNameItr = ColNames.iterator();
						while(colNameItr.hasNext())
						{
							try
							{
								Cell cellValue = cellN.next();
								if(cellValue.getCellTypeEnum()==CellType.STRING)
								{
									if(ColNames.get(key).equals("Testcase"))
									{
										data.put(ColNames.get(key), testname);
									}
									else 
									{
										data.put(ColNames.get(key), cellValue.getStringCellValue());
									}
								}
								else if(cellValue.getCellTypeEnum()==CellType.NUMERIC)
								{
									String cellData = NumberToTextConverter.toText(cellValue.getNumericCellValue());
									data.put(ColNames.get(key), cellData);
								}
								key++;
							}
							catch(Exception e)
							{
								break;
							}
						}
						listMap.add(data);
						key=0;
					}
				}
				else
				{
					System.err.println("#### Test case name is not present in test data sheet ####");
				}
				
			}
		}
		//System.out.println(listMap);
	return listMap;
  }
	
	public void WriteInPartiularCellInExistingFile(int rowNum, int cellNum, String value)
	{
		try 
		{
			FileInputStream file = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			int sheetNo = workbook.getNumberOfSheets();
			for(int i=0; i<sheetNo; i++)
			{
				if(workbook.getSheetName(i).equalsIgnoreCase(wrkSheetName))
				{
					XSSFSheet sheetName = workbook.getSheetAt(i);
					Row row = sheetName.createRow(rowNum);
					Cell cell = row.createCell(cellNum);
					cell.setCellValue(value);
				}				
			}
			
			FileOutputStream outStream = new FileOutputStream(filePath);
			workbook.write(outStream);
			workbook.close();
			outStream.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		//new ExcelDataReader("D:\\NK\\API\\APIProjects\\RestAssuredCucumber\\testdata\\servicedata\\Book1.xlsx", "Sheet1", "Verify Ping request is run successfully").countTestData();
		try {
			System.out.println(new ExcelDataReader("D:\\NK\\Selenium Projects\\Amazon-BDD\\src\\main\\java\\com\\amazon\\testdata\\sit\\Book1.xlsx", "Sheet1", "Verify Ping").getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
