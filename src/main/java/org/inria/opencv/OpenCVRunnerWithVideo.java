package org.inria.opencv;

import org.inria.freenect.FreenectRunner;
import org.inria.freenect.KinectManager;
import org.inria.freenect.sample.DepthHandlerImpl;
import org.inria.opencv.freenect.sample.OpenCVFaceDetectionHandler;
import org.openkinect.freenect.DepthFormat;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 09:02
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class OpenCVRunnerWithVideo extends FreenectRunner {


    public static void main(String[] args) {
        new OpenCVManager().initializeOpenCV();

        DepthHandlerImpl depthHandler = new DepthHandlerImpl(640, 480, DepthFormat.D11BIT);
        OpenCVFaceDetectionHandler videoHandler = new OpenCVFaceDetectionHandler("/home/edaubert/bin/opencv/share/OpenCV/lbpcascades/lbpcascade_frontalface.xml", 640, 480);

        KinectManager manager = new KinectManager();
        manager.initializeContext();
        manager.initializeDevice(0);
        manager.defineDepth(depthHandler);
        manager.defineVideo(videoHandler);

        displayDepth(depthHandler, manager);
        displayVideo(videoHandler, manager);
    }
}
