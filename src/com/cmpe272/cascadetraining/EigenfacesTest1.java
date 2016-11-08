package com.cmpe272.cascadetraining;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Vector;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;

import com.cmpe272.facedetection.utilities.ProjectProperties;
import com.cmpe272.facedetection.utilities.PropertiesLoader;

public class EigenfacesTest1 {
	public static void main(String[] args) {
		PropertiesLoader.loadProperties();
		File trainingCSVFile = new File(ProjectProperties.trainingBasePath + File.separator + ProjectProperties.trainingSet + ".txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(trainingCSVFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Vector<String> imageStrings = new Vector<String>();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				imageStrings.addElement(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		MatVector images = new MatVector(imageStrings.size());
		Mat labels = new Mat(imageStrings.size(), 1, CV_32SC1);
		IntBuffer labelsBuf = labels.getIntBuffer();
		int counter = 0;
		for (String image : imageStrings) {
			String[] parts = image.split("[" + ProjectProperties.csvSeparator + "]");
			String imageAbsolutePath = parts[0];
			String imageLabel = parts[1];
			Mat img = imread(imageAbsolutePath, CV_LOAD_IMAGE_GRAYSCALE);
			int label = Integer.parseInt(imageLabel);
			images.put(counter, img);
			labelsBuf.put(counter, label);
			counter++;
		}

		FaceRecognizer faceRecognizer = createFisherFaceRecognizer();

		faceRecognizer.train(images, labels);
		
		File inputFile = new File("C:\\Users\\keyur\\workspace\\OnYourFace\\inputFiles\\FaceDetections\\Celebrities\\Anjelina Jolie\\0ajyfuy (Custom) (Custom).jpg");

		int predictedLabel = faceRecognizer.predict(imread(inputFile.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE));

		System.out.println("Predicted label: " + predictedLabel);
	}
}