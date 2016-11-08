package com.cmpe272.facedetection.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	
	static Properties properties;
	
	public static void loadProperties() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("projectProperties.properties"));
			ProjectProperties.inputFileImagePath = properties.getProperty("inputFileImagePath").trim();
			ProjectProperties.outputFileImagePath = properties.getProperty("outputFileImagePath").trim();
			ProjectProperties.frontalFaceXMLPath = properties.getProperty("frontalFaceXMLPath").trim();
			ProjectProperties.trainingBasePath = properties.getProperty("trainingBasePath").trim();
			ProjectProperties.trainingSet = properties.getProperty("trainingSet").trim();
			ProjectProperties.csvSeparator = properties.getProperty("csvSeparator").trim();
			ProjectProperties.imageExtension = properties.getProperty("imageExtension").trim();
			
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}
