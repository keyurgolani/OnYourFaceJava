package com.cmpe272.facedetection.utilities;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

public class Utilities {
	
	public static String pathSeparator = "\\";
	
	public static boolean isImageFile(File inputFile) {
		boolean isImage = false;
		if(inputFile.isFile()) {
			String mimetype = new MimetypesFileTypeMap().getContentType(inputFile);
			String type = mimetype.split("/")[0];
			if(type.equals("image")) isImage = true;
		}
		return isImage;
	}

}
