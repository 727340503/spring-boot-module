package com.cherrypicks.tcc.cms.api;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cherrypicks.tcc.util.Json;

public class GentratorProjectVersion {
	
	private final static List<String> modules = new ArrayList<String>();
	
	static {
		modules.add("tcc-system-web");
		modules.add("tcc-merchant-web");
		modules.add("tcc-campaign-web");
		modules.add("tcc-promotion-web");
		modules.add("tcc-customer-web");
		modules.add("tcc-push-web");
		modules.add("tcc-keeper-web");
		modules.add("tcc-report-web");
	}
	
	@SuppressWarnings({"rawtypes"})
	public static void main(String[] args) throws Exception {
		File pomFile = new File("./pom.xml");
		File jsonFile = new File("./target/module_version.json");
		Map<String,String> moduleJson = new HashMap<String,String>(); 
		
		if(pomFile.exists()) {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(pomFile);
			
			Element dependencies = doc.getRootElement().element("dependencies");
			Element dependency;
			for(Iterator i = dependencies.elementIterator("dependency"); i.hasNext();) {
				dependency = (Element) i.next();
				if(modules.contains(dependency.elementText("artifactId"))) {
					moduleJson.put(dependency.elementText("artifactId"), dependency.elementText("version"));
				}
			}
			
			if(jsonFile.exists()) {
				jsonFile.delete();
			}
			
			FileOutputStream fos = new FileOutputStream(jsonFile);
			fos.write(Json.toJson(moduleJson).getBytes());
			fos.close();
		}
	}

}
