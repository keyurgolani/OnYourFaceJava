package com.cmpe272.facedetection;
 
import java.io.File;
import java.io.FilenameFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.cmpe272.facedetection.utilities.ProjectProperties;
import com.cmpe272.facedetection.utilities.PropertiesLoader;
 
public class FaceDetector {
 
    public static void main(String[] args) {
 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");
        PropertiesLoader.loadProperties();
        CascadeClassifier faceDetector = new CascadeClassifier(ProjectProperties.frontalFaceXMLPath);
        File inputPath = new File(ProjectProperties.inputFileImagePath);
        File[] inputFilesList = inputPath.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
			}
		});
        for(File inputFile : inputFilesList) {
        	Mat image = Imgcodecs.imread(inputFile.getPath());
        	MatOfRect faceDetections = new MatOfRect();
        	faceDetector.detectMultiScale(image, faceDetections);
        	System.out.println(faceDetections.toArray().length + " faces Detected for Image File " + inputFile.getName());
        	int i = 0;
        	Mat face;
        	Mat resizedFace;
        	for (Rect rect : faceDetections.toArray()) {
                face = new Mat(image, new Rect(rect.x, rect.y, rect.width, rect.height));
                resizedFace = new Mat();
                Size size = new Size(200, 200);
                Imgproc.resize(face, resizedFace, size);
                Imgcodecs.imwrite(ProjectProperties.outputFileImagePath + File.separator + i + inputFile.getName(), face);
                i++;
            }
        }
    }
}