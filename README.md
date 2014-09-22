Chenso
======

[![Build Status](https://travis-ci.org/sangar/Chenso.svg?branch=master)](https://travis-ci.org/sangar/Chenso)

Chenso is a org.w3c.dom XML wrapper/parser library.

### Usage

```java

String xmlString = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";

ChensoXMLDocument document = ChensoXMLDocument.XMLDocumentWithXMLString(xmlString);
ChensoXMLElement rootElement = document.getRootElement();

String to = rootElement.firstValueForNodeName("to");
String from = rootElement.firstValueForNodeName("from");
String reminder = rootElement.firstValueForNodeName("heading");
String body = rootElement.firstValueForNodeName("body");

```

## Credits

Chenso is based upon the iOS & Mac OS X library Ono (https://github.com/mattt/Ono)
