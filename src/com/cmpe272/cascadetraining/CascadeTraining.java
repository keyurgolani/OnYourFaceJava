package com.cmpe272.cascadetraining;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import com.cmpe272.facedetection.utilities.ProjectProperties;
import com.cmpe272.facedetection.utilities.PropertiesLoader;

public class CascadeTraining {

	public static void main(String[] args) {
		PropertiesLoader.loadProperties();
		try {
			File trainingCSV = createTrainingCSV(ProjectProperties.trainingBasePath, ProjectProperties.trainingSet);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static File createTrainingCSV(String trainingBasePath, String trainingSet) throws FileNotFoundException {
		File trainingCSVFile = new File(trainingBasePath + File.separator + trainingSet + ".txt");
		if (trainingCSVFile.exists()) {
			trainingCSVFile.delete();
		}
		PrintWriter pw = new PrintWriter(trainingCSVFile);
		File trainingSetFile = new File(trainingBasePath + File.separator + trainingSet);
		int label = 0;
		for (File imageSet : trainingSetFile.listFiles()) {
			if (imageSet.isDirectory()) {
				Collection<File> images = FileUtils.listFiles(imageSet, new RegexFileFilter("^([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)"), DirectoryFileFilter.DIRECTORY);
//				FilenameFilter imgFilter = new FilenameFilter() {
//					public boolean accept(File dir, String name) {
//						name = name.toLowerCase();
//						return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
//					}
//				};
//				File[] images = imageSet.listFiles(imgFilter);
				for (File image : images) {
					pw.println(image.getAbsolutePath() + ProjectProperties.csvSeparator + label);
				}
				label++;
			}
		}
		pw.close();
		return trainingCSVFile;
	}

}
