Chenso
======

[![Build Status](https://travis-ci.org/sangar/Chenso.svg?branch=master)](https://travis-ci.org/sangar/Chenso) [![Platform](http://img.shields.io/badge/platform-java%7Candroid-lightgrey.svg)](https://github.com/JavaNetworking/JavaNetworking) [![License](http://img.shields.io/badge/license-MIT-red.svg)](http://opensource.org/licenses/MIT)

Chenso is a org.w3c.dom XML wrapper/parser library.

## Basic usage

### Run tests

##### Windows
```cmd
gradlew.bat test
```

##### Unix
```bash
./gradlew test
```

### Build

##### Windows
```cmd
gradlew.bat build
```

##### Unix
```bash
./gradlew build
```

### Usage

#### Get first value for name in XML element
```java
String xmlString = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";

ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);
ChensoXMLElement rootElement = document.getRootElement();

String to = rootElement.firstValueForNodeName("to");
String from = rootElement.firstValueForNodeName("from");
String reminder = rootElement.firstValueForNodeName("heading");
String body = rootElement.firstValueForNodeName("body");

```

#### Get list of XML elements in document
```java
String xmlString = "<notes><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note><note><to>Jani</to><from>Tove</from><heading>Reminder</heading><body>Don't forget me either!</body></note></notes>";

ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);
List<ChensoXMLElement> elements = document.getRootElement().getChildren();

for (ChensoXMLElement element : elements) {
	String to = element.firstValueForNodeName("to");
	String from = element.firstValueForNodeName("from");
	String heading = element.firstValueForNodeName("heading");
	String body = element.firstValueForNodeName("body");
}
```

#### Iterate through document using XPath
```java
String xmlString = "<notes><note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note><note><to>Jani</to><from>Tove</from><heading>Reminder</heading><body>Don't forget me either!</body></note></notes>";

ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);

document.enumerateElementsWithXPath("//note", new XPathCallback() {
	public void update(ChensoXMLElement element, int index) {
		String to = element.firstValueForNodeName("to");
		String from = element.firstValueForNodeName("from");
		String heading = element.firstValueForNodeName("heading");
		String body = element.firstValueForNodeName("body");

		...
	}
});
```

## Credits

Chenso is based on an iOS & Mac OS X library called - [Ono](https://github.com/mattt/Ono).
