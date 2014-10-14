package com.chenso.lib;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

// Chainsaw XML lib
public class ChensoXMLDocument {
	
	// The XML document to handle
	private Document document;

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

	public ChensoXMLDocument(Document document) {
		if (document == null) {
			throw new NullPointerException("Document cannot be null");
		}
		this.document = document;
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
			
			this.document = document;
		} catch (Exception e) {
			throw new NullPointerException("Document cannot be null");
		}
	}
	
	public ChensoXMLElement getRootElement() {
		if (this.rootElement == null) {
			this.rootElement = elementWithNode(this.document.getDocumentElement());
		}
		return this.rootElement;
	}
	
	public ChensoXMLElement elementWithNode(Node node) {
		if (node == null) {
			return null;
		}
		return new ChensoXMLElement(this, node);
	}

	public interface XPathCallback {
		void update(ChensoXMLElement element, int index);
	}

	public void enumerateElementsWithXPath(String XPath, XPathCallback callback) {
		if (callback == null) {
			return;
		}
		
		int index = 0;
		for (ChensoXMLElement element : this.XPath(XPath)) {
			callback.update(element, index++);
		}
	}

	public List<ChensoXMLElement> XPath(String XPath) {
		if (XPath.isEmpty()) {
			return null;
		}
		
		
		List<ChensoXMLElement> elements = new ArrayList<ChensoXMLDocument.ChensoXMLElement>();
		javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = null;
		try {
			nodes = (NodeList)xPath.evaluate(XPath, this.document.getDocumentElement(), XPathConstants.NODESET);
			
			for (int i = 0; i < nodes.getLength(); ++i) {
			    elements.add(this.elementWithNode(nodes.item(i)));
			}
			
			return elements;
		} catch (XPathExpressionException e) {}
		
		return null;
	}

	//
	// ChensoXMLElement
	//
	
	private ChensoXMLElement rootElement;

	public class ChensoXMLElement {
		private ChensoXMLDocument document;
		private Node xmlNode;

		public ChensoXMLElement(ChensoXMLDocument document, Node xmlNode) {
			this.document = document;
			this.xmlNode = xmlNode;
		}

		public Node getXMLNode() {
			return xmlNode;
		}

		/**
		 Generates an integer list generator which is used to generate acceptable response codes.
		 
		 The list generated starts at the {@param start} value and stops at value {@param stop} - 1.
		 
		 @param start Start integer of the list of integers.
		 @param stop Stop integer, stops at value - 1. If 300 is given, stop value is 299.
		 */
		public List<Integer> range(int start, int stop) {
			List<Integer> range = new ArrayList<Integer>(stop-start);
			
			for (int i=0; i< stop-start; i++) {
				range.add(i, start+i);
			}
			
			return range;
		}

		public List<ChensoXMLElement> getChildren() {
			List<Integer> indexes = range(0, 0xffff);
			
			return getChildrenAtIndexes(indexes);
		}

		public List<ChensoXMLElement> getChildrenAtIndexes(List<Integer> indexes) {
			List<ChensoXMLElement> children = new ArrayList<ChensoXMLDocument.ChensoXMLElement>();
			
			NodeList nodes = this.xmlNode.getChildNodes();
			for (Integer i=0; i<nodes.getLength(); i++) {
				Node node = nodes.item(i);
			
				if (indexes.contains(i) && node.getNodeType() == Node.ELEMENT_NODE) {
					children.add(elementWithNode(node));
				}
			}
			return children;
		}
		
		public String firstValueForNodeName(String nodeName) {
			return valueForNodeInNodes(nodeName, null, this.xmlNode.getChildNodes());
		}
		
		public String firstValueForAttributeName(String attrName) {
			return valueForNodeInNodes(null, attrName, this.xmlNode.getChildNodes());
		}
		
		private String valueForNodeInNodes(String nodeName, String attrName, NodeList nodeList) {
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
				}
		    }
			
			return "";
		}
	}
}
