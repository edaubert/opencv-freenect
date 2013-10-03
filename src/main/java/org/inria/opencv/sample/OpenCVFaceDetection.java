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

    public OpenCVFaceDetection(String cascadeClassifierPath) {
        faceDetector = new CascadeClassifier(cascadeClassifierPath);
    }

    public MatOfRect detect(Mat image) {

        Mat tmp = image;
        if (image.depth() != CvType.CV_8U) {
            image.convertTo(tmp, CvType.CV_8U);
        }

        // MatOfRect is a special container class for Rect.
        MatOfRect faces = new MatOfRect();
        // Detect faces in the image.
        faceDetector.detectMultiScale(tmp, faces);
        return faces;
    }

    private void drawRectForFace(Mat image, MatOfRect faces) {
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
