package config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class AbstractConfig {

	protected Document document;
	
	
	protected Document getDocumentByFileAddress(String fileAddress) throws DocumentException{
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(new File(fileAddress));
		return document;
	}

}
