package com.chenso.lib;

import static org.junit.Assert.*;

import org.junit.Test;

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
	public void testFirstValueForAttributeName() {
		ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);

		assertEquals("firstname", document.getRootElement().firstValueForAttributeName("type"));
	}
}