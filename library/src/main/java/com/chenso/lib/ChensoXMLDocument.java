package com.chenso.lib;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

// Chainsaw XML lib
public class ChensoXMLDocument {
	
	// The XML document to handle
	private Document doc;

	//
	// Static constructors
	//
	
	public static ChensoXMLDocument XMLDocumentWithData(byte[] data) {
		return new ChensoXMLDocument(data);
	}
	
	public static ChensoXMLDocument XMLDocumentWithXMLString(String xmlString) {
		return new ChensoXMLDocument(xmlString);
	}
	
	public static ChensoXMLDocument XMLDocumentWithDocument(Document doc) {
		return new ChensoXMLDocument(doc);
	}
	
	//
	// Constructors 
	//
	
	public ChensoXMLDocument(Document doc) {
		if (doc == null) {
			throw new NullPointerException("Document cannot be null");
		}
		this.doc = doc;
	}
	
	public ChensoXMLDocument(byte[] data) {
		this(new String(data));
	}
	
	public ChensoXMLDocument(String xmlString) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xmlString)));
			document.getDocumentElement().normalize();
			
			this.doc = document;
		} catch (Exception e) {
			throw new NullPointerException("Document cannot be null");
		}
	}
	
	//
	// Public methods
	//
	
	public String firstValueForNodeName(String nodeName) {
		return firstValueForNodeInDocument(nodeName, this.doc);
	}
	
	public String firstValueForAttributeName(String attrName) {
		return firstValueForAttributeInDocument(attrName, this.doc);
	}
	
	//
	// Private methods
	//
	
	private static String valueForNodeInNodes(String nodeName, String attrName, NodeList nodeList) {
		if (nodeName == null) {
			nodeName = "";
		}
		if (attrName == null) {
			attrName = "";
		}
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node tempNode = nodeList.item(i);
	    	if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
	    		// get node name and value
	    		if (nodeName.equalsIgnoreCase(tempNode.getNodeName())) {
	    			return tempNode.getTextContent();
	    		}
				
				if (tempNode.hasAttributes()) {
		 			// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
		 			for (int j = 0; j < nodeMap.getLength(); j++) {
		 				Node node = nodeMap.item(j);
						if (attrName.equalsIgnoreCase(node.getNodeName())) {
							return node.getNodeValue();
						}
					}
				}
		 
				if (tempNode.hasChildNodes()) {
					return valueForNodeInNodes(nodeName, attrName, tempNode.getChildNodes());
				}
			}
	    }
		
		return "";
	}
	
	private String firstValueForNodeInDocument(String nodeName, Document doc) {
		String retval = "";
		
		if (doc.hasChildNodes()) {
			retval = valueForNodeInNodes(nodeName, null, doc.getChildNodes());
		}
		
		return retval;
	}
	
	private String firstValueForAttributeInDocument(String attrName, Document doc) {
		String retval = "";
		
		if (doc.hasChildNodes()) {
			retval = valueForNodeInNodes(null, attrName, doc.getChildNodes());
		}
		
		return retval;
	}
	
}
