package com.amazon.filereader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.amazon.utils.Utility;

public class BrowserEnvironmentReader extends XMLReader
{
	private Map<String, List<String>> enviromentMap;
	
	public BrowserEnvironmentReader()
	{
		readFile();
	}
	
	public Map<String, List<String>> getBrowserEnvironment() {
		return enviromentMap;
	}
	
	private void readFile()
	{
		System.out.println("#### Reading Browser Environment ####");
		try {
			XMLEventReader eventReader = getXMLEventReader(
					new FilePathFinder("browserconfiguration.xml").getFilePath());
			enviromentMap = getEnvironmentData(eventReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, List<String>> getEnvironmentData(XMLEventReader eventReader) {
		Map<String, List<String>> envMap = new HashMap<String, List<String>>();
		try
		{
			while(eventReader.hasNext())
			{
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) 
				{
					StartElement startElement = event.asStartElement();
					String elementName = startElement.getName().getLocalPart();
					if(elementName.equals("Environments"))
					{
						while(eventReader.hasNext())
						{
							XMLEvent envEvent = eventReader.nextEvent();
							if(envEvent.isStartElement()) 
							{
								StartElement envStartElement = envEvent.asStartElement();
								String envElementName = envStartElement.getName().getLocalPart();
								//System.out.println(envElementName);
								if(envElementName.equals("QAEnvironment"))
								{
									Iterator<Attribute> qaEnvAttrItr = envStartElement.getAttributes();
									while(qaEnvAttrItr.hasNext())
									{
										Attribute qaEnvAttr = qaEnvAttrItr.next();
										if(qaEnvAttr.getName().getLocalPart().equals("testingExecutionStatus") && qaEnvAttr.getValue().equals("True"))
										{
											while(eventReader.hasNext())
											{
												XMLEvent qaEnvEvent = eventReader.nextEvent();
												if(qaEnvEvent.isStartElement())
												{
													StartElement qaEnvStartElement = qaEnvEvent.asStartElement();
													String qaEnvElementName = qaEnvStartElement.getName().getLocalPart();
													//System.out.println(qaEnvElementName);
													if(qaEnvElementName.equals("BrowserName"))
													{
														Iterator<Attribute> browserEleAttrItr = qaEnvStartElement.getAttributes();
														while(browserEleAttrItr.hasNext())
														{
															Attribute browserEleAttr = browserEleAttrItr.next();
															if(browserEleAttr.getName().getLocalPart().equals("browserFlag") && browserEleAttr.getValue().equals("True"))
															{
																browserEleAttr = browserEleAttrItr.next();
																if(browserEleAttr.getName().toString().equals("value"))
																{
																	String browserName = browserEleAttr.getValue();
																	envMap.put("BrowserName", Utility.getListData(browserName));
																}
																while(eventReader.hasNext())
																{
																	XMLEvent browserEnvEvent = eventReader.nextEvent();
																	if(browserEnvEvent.isStartElement())
																	{
																		StartElement browserEnvStartElement = browserEnvEvent.asStartElement();
																		String browserEnvElementName = browserEnvStartElement.getName().getLocalPart();
																		//System.out.println(browserEnvElementName);
																		if(browserEnvElementName.equals("BrowserPath"))
																		{
																			browserEnvEvent = eventReader.nextEvent();
																			envMap.put("BrowserPath", Utility.getListData(browserEnvEvent.isCharacters() ? browserEnvEvent.asCharacters().getData().trim() : ""));
																			
																		}
																		if(browserEnvElementName.equals("WindowMaximize"))
																		{
																			browserEnvEvent = eventReader.nextEvent();
																			envMap.put("WindowMaximize", Utility.getListData(browserEnvEvent.isCharacters() ? browserEnvEvent.asCharacters().getData().trim() : ""));
																			
																		}
																		if(browserEnvElementName.equals("URL"))
																		{
																			browserEnvEvent = eventReader.nextEvent();
																			envMap.put("URL", Utility.getListData(browserEnvEvent.isCharacters() ? browserEnvEvent.asCharacters().getData().trim() : ""));
																		}
																		if(browserEnvElementName.equals("ImplicitWait"))
																		{
																			browserEnvEvent = eventReader.nextEvent();
																			envMap.put("ImplicitWait", Utility.getListData(browserEnvEvent.isCharacters() ? browserEnvEvent.asCharacters().getData().trim() : ""));
																		}
																		if(browserEnvElementName.equals("TestDataFolderPath"))
																		{
																			browserEnvEvent = eventReader.nextEvent();
																			envMap.put("TestDataFolderPath", Utility.getListData(browserEnvEvent.isCharacters() ? browserEnvEvent.asCharacters().getData().trim() : ""));
																			
																		}
																	}
																	else if(browserEnvEvent.isEndElement())
																	{
																		EndElement endElement = browserEnvEvent.asEndElement();
																		if(endElement.getName().getLocalPart().equals("BrowserName"))
																		{
																			//System.err.println("BrowserName end element is reached");
																			break;
																		}
																	}
																}
															}
														}
													}
												}
												else if(qaEnvEvent.isEndElement())
												{
													EndElement endElement = qaEnvEvent.asEndElement();
													if(endElement.getName().getLocalPart().equals("QAEnvironment"))
													{
														//System.err.println("QAEnvironment end eleent is reacched");
														break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return envMap;
	}
	
	public static void main(String[] args) {
		BrowserEnvironmentReader reader = new BrowserEnvironmentReader();
		System.out.println(reader.getBrowserEnvironment());
		//System.out.println(reader.ReadXML());
	}
}
