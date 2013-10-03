package org.inria.opencv.freenect.sample;

import org.inria.opencv.freenect.AbstractOpenCVVideoHandler;
import org.inria.opencv.sample.OpenCVFaceDetection;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 11:14
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class OpenCVFaceDetectionHandler extends AbstractOpenCVVideoHandler {

    private OpenCVFaceDetection faceDetector;

    public OpenCVFaceDetectionHandler(String cascadeClassifierPath, int width, int height) {
        super(width, height);
        faceDetector = new OpenCVFaceDetection(cascadeClassifierPath);
    }

    @Override
    protected void manipulateFrame() {
        // TODO build Mat
        Mat imageMat = new MatOfInt(videoFrame);//videoFrame
        faceDetector.detectAndDrawRect(imageMat);
    }
}
