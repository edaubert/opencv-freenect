package org.inria.opencv;

import org.inria.display.opencv.IndependantDisplayFrame;
import org.inria.opencv.sample.OpenCVFaceDetection;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.Highgui;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 09:02
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class OpenCVRunnerWithImage {

    public static void main(String[] args) {
        new OpenCVManager().initializeOpenCV();

        Mat image = Highgui.imread("/home/edaubert/Bureau/image.jpeg");

//        Mat image = Highgui.imread("/home/edaubert/Bureau/MOSCOW-articleLarge-v2.jpg");

        OpenCVFaceDetection faceDetector = new OpenCVFaceDetection("/home/edaubert/bin/opencv/share/OpenCV/lbpcascades/lbpcascade_frontalface.xml");
        MatOfRect faces = faceDetector.detect(image);

        System.out.println(String.format("Detected %s faces", faces.toArray().length));

        faceDetector.detectAndDrawRect(image);

        IndependantDisplayFrame displayFrame = new IndependantDisplayFrame("Image Display", image.width(), image.height());
        displayFrame.buildFrameImage(image);
        displayFrame.displayFrame();

    }
}
