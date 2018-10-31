package atlinject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import atlinject.utils.OCLAttributeDerived;

public class XMIParser {
	
	private static XMIParser instance;
	
	private XMIParser() {};
	
	public static XMIParser getInstance() {
		if(instance == null)
			instance = new XMIParser();
		return instance;
	}

	public List<OCLAttributeDerived> parse(File file) {
		
		List<OCLAttributeDerived> list = new ArrayList<OCLAttributeDerived>();

		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			if (doc.hasChildNodes()) {
				list = lookUpFor(doc.getChildNodes(), "xsi:type", "atl:MatchedRule");
			}
		} catch (Exception e) {System.out.println(e.getMessage());}
				
		return list;
	}
	
	
	private static List<OCLAttributeDerived> lookUpFor(NodeList nodeList, String name, String value) {
		List<OCLAttributeDerived> oclAttrList = new ArrayList<OCLAttributeDerived>();
		
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				Element el = (org.w3c.dom.Element) tempNode;
				
				if(el.hasAttribute(name) && el.getAttribute(name).equals(value) ){
					oclAttrList.add( new OCLAttributeDerived(value, el.getAttribute("name")));
					
					List<OCLAttributeDerived> modElemAttributes = new ArrayList<OCLAttributeDerived>();
					List<OCLAttributeDerived> modElemList = new ArrayList<OCLAttributeDerived>();
					
					if (tempNode.hasChildNodes()) {
						modElemList = lookUpFor(tempNode.getChildNodes(), "xsi:type", "ocl:OclModelElement");				
					}
					
					if (tempNode.hasChildNodes()) {
						modElemAttributes = lookUpFor(tempNode.getChildNodes(), "xsi:type", "ocl:NavigationOrAttributeCallExp");
					}				
					
					for(int i = 1;i<modElemList.size();i++) {oclAttrList.add(modElemList.get(i));}					
					for(OCLAttributeDerived s:modElemAttributes) {oclAttrList.add(s);}
					
				} else {
					if (tempNode.hasChildNodes()) {
						oclAttrList.addAll(lookUpFor(tempNode.getChildNodes(), name, value));
					}
				}
			}
		}		
		return oclAttrList;
	}
	
}
