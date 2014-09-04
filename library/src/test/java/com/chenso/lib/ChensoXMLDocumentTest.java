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

public class ChensoXMLDocumentTest {

	private final String xmlString = "<note><to type=\"firstname\">Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";

	@Test
	public void testFirstValueForNodeName() {
		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);
		
		assertEquals("Tove", document.getRootElement().firstValueForNodeName("to"));
		assertEquals("Jani", document.getRootElement().firstValueForNodeName("from"));
		assertEquals("Reminder", document.getRootElement().firstValueForNodeName("heading"));
		assertEquals("Don't forget me this weekend!", document.getRootElement().firstValueForNodeName("body"));
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
		
		assertEquals("Tove", document.getRootElement().firstValueForNodeName("to"));
		assertEquals("Jani", document.getRootElement().firstValueForNodeName("from"));
		assertEquals("Reminder", document.getRootElement().firstValueForNodeName("heading"));
		assertEquals("Don't forget me this weekend!", document.getRootElement().firstValueForNodeName("body"));
	}
	
	@Test
	public void testFirstValueForAttributeName() {
		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);

		assertEquals("firstname", document.getRootElement().firstValueForAttributeName("type"));
	}
}