package com.amazon.filereader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

public abstract class XMLReader
{
	
	public XMLEventReader getXMLEventReader(String filePath) throws XMLStreamException, IOException
	{
		
		try
		{
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(filePath);
           return inputFactory.createXMLEventReader(in);
            
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
            e.toString();
            throw new FileNotFoundException(" file path is incorrect");
        } catch (XMLStreamException e) {
        	  e.printStackTrace();
              e.toString();
              throw new XMLStreamException(" XMLStreamException");
        }
	}
}
