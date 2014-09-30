package com.chenso.lib;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

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
	private final String xmlElements = "<notes><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note><note><to>Jani</to><from>Tove</from><heading>Reminder</heading><body>Don't forget me either!</body></note></notes>";

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

	@Test
	public void testGetChildrenFromRootElement() {

		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlElements);
		List<ChensoXMLElement> elements = document.getRootElement().getChildren();

		assertEquals(2, elements.size());

		for (ChensoXMLElement element : elements) {
			String to = element.firstValueForNodeName("to");
			String from = element.firstValueForNodeName("from");
			String reminder = element.firstValueForNodeName("heading");
			String body = element.firstValueForNodeName("body");

			assertNotNull(to);
			assertNotNull(from);
			assertNotNull(reminder);
			assertNotNull(body);
		}
	}
}