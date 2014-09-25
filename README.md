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

Chenso is based on an iOS & Mac OS X library called - [Ono](https://github.com/mattt/Ono).
