package org.inria.opencv.freenect.sample;

import org.inria.freenect.sample.VideoHandlerImpl;
import org.inria.opencv.sample.OpenCVFaceDetection;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openkinect.freenect.FrameMode;
import org.openkinect.freenect.VideoFormat;

import java.nio.ByteBuffer;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 11:14
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class OpenCVFaceDetectionHandler extends VideoHandlerImpl {

    private OpenCVFaceDetection faceDetector;
    private Mat imageMat;
//    private Mat internalMat;

    private byte[] bytes;

    public OpenCVFaceDetectionHandler(String cascadeClassifierPath, int width, int height) {
        super(width, height, VideoFormat.RGB);
        faceDetector = new OpenCVFaceDetection(cascadeClassifierPath);

        imageMat = new Mat(height, width, CvType.CV_8UC3);
//        internalMat = new Mat(height, width, CvType.CV_8UC1);

        bytes = new byte[3 * height * width];
    }

    @Override
    public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
        frame.get(bytes);
        frame.position(0);
        imageMat.put(0, 0, bytes);

//        Imgproc.cvtColor(imageMat, internalMat, Imgproc.COLOR_BGR2GRAY);

//        MatOfRect faces = faceDetector.detect(imageMat);
//        faceDetector.drawRectForFace(imageMat, faces);
        faceDetector.detectAndDrawRect(imageMat);

        imageMat.get(0, 0, bytes);
        frame.put(bytes);
        frame.position(0);

        super.onFrameReceived(mode, frame, timestamp);
    }
}
