package com.chenso.lib;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.chenso.lib.ChensoXMLDocument.ChensoXMLElement;

public class ChensoXMLDocumentTest {

	private final String xmlString = "<note><to type=\"firstname\">Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";

	@Test
	public void testFirstValueForNodeName() {
		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);
		ChensoXMLElement rootElement = document.getRootElement();
		
		assertEquals("Tove", rootElement.firstValueForNodeName("to"));
		assertEquals("Jani", rootElement.firstValueForNodeName("from"));
		assertEquals("Reminder", rootElement.firstValueForNodeName("heading"));
		assertEquals("Don't forget me this weekend!", rootElement.firstValueForNodeName("body"));
	}

	@Test
	public void testFirstValueForNodeNameFromDocument() {
		
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xmlString)));
			doc.getDocumentElement().normalize();
		} catch(Exception e) {}
		
		assertNotNull(doc);
		
		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithDocument(doc);
		ChensoXMLElement rootElement = document.getRootElement();
		
		assertEquals("Tove", rootElement.firstValueForNodeName("to"));
		assertEquals("Jani", rootElement.firstValueForNodeName("from"));
		assertEquals("Reminder", rootElement.firstValueForNodeName("heading"));
		assertEquals("Don't forget me this weekend!", rootElement.firstValueForNodeName("body"));
	}
	
	@Test
	public void testFirstValueForAttributeName() {
		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);
		ChensoXMLElement rootElement = document.getRootElement();

		assertEquals("firstname", rootElement.firstValueForAttributeName("type"));
	}
}