package org.inria.opencv.sample;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 11:14
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class OpenCVFaceDetection {

    private CascadeClassifier faceDetector;
    private Mat internalMat;

    public OpenCVFaceDetection(String cascadeClassifierPath) {
        faceDetector = new CascadeClassifier(cascadeClassifierPath);
        internalMat = new MatOfInt();
    }

    public MatOfRect detect(Mat image) {
        if (image.depth() != CvType.CV_8U) {
            internalMat.create(image.height(), image.width(), CvType.CV_8U);
            image.convertTo(internalMat, CvType.CV_8U);
        } else {
            // avoid to modify the given image
            // Moreover without this clone faces are not detected ...
            internalMat = image.clone();
        }

        // MatOfRect is a special container class for Rect.
        MatOfRect faces = new MatOfRect();
        // Detect faces in the image.
        faceDetector.detectMultiScale(internalMat, faces);
        return faces;
    }

    public void drawRectForFace(Mat image, MatOfRect faces) {
        // Draw a bounding box around each face.
        for (Rect rect : faces.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
    }

    public void detectAndDrawRect(Mat image) {
        MatOfRect faces = detect(image);
        drawRectForFace(image, faces);
    }
}
