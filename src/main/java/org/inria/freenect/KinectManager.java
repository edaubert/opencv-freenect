package org.inria.freenect;

import org.openkinect.freenect.*;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 09:02
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class KinectManager {

    public static Context ctx;
    public Device dev;

    private VideoFormat videoFormat;
    private DepthFormat depthFormat;



    public boolean initializeContext() {
        if (ctx == null) {
            ctx = Freenect.createContext();
            return ctx != null;
        } else {
            return true;
        }
    }

    public boolean initializeDevice(int deviceNumber) {
        if (ctx.numDevices() > 0) {
            dev = ctx.openDevice(deviceNumber);
            return dev != null;
        } else {
            return false;
        }
    }

    public void freeDevice() {
        if (ctx != null) {
            if (dev != null) {
                dev.stopDepth();
                dev.stopVideo();
                dev.close();
            }
        }
    }

    public void freeContext() {
        if (ctx != null) {
            ctx.shutdown();
        }
    }

    public void defineDepth(DepthHandler handler) {
        dev.setDepthFormat(handler.getFormat());
        depthFormat = handler.getFormat();
        dev.startDepth(handler);
    }

    public void defineVideo(VideoHandler  handler) {
        dev.setVideoFormat(handler.getFormat());
        videoFormat = handler.getFormat();
        dev.startVideo(handler);
    }
}
