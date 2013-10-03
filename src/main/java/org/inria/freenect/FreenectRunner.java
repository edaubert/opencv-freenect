package org.inria.freenect;

import org.inria.display.CloseKeyListener;
import org.inria.display.freenect.IndependantDisplayFrame;
import org.inria.freenect.sample.DepthHandlerImpl;
import org.inria.freenect.sample.VideoHandlerImpl;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 09:02
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class FreenectRunner {

    public static void main(String[] args) {
        DepthHandlerImpl depthHandler = new DepthHandlerImpl(640, 480);
        VideoHandlerImpl videoHandler = new VideoHandlerImpl(640, 480);

        KinectManager manager = new KinectManager();
        manager.initializeContext();
        manager.initializeDevice(0);
        manager.defineDepth(depthHandler);
        manager.defineVideo(videoHandler);

        displayDepth(depthHandler, manager);
        displayVideo(videoHandler, manager);
    }

    protected static void displayDepth(final DepthHandler depthHandler, final KinectManager manager) {
        new Thread(new Runnable() {
            private int[] frame = new int[640 * 480];

            @Override
            public void run() {
                IndependantDisplayFrame displayFrame = new IndependantDisplayFrame("Depth Display", 640, 480);
                displayFrame.addKeyListener(CloseKeyListener.getInstance(manager));
                while (!CloseKeyListener.getInstance(manager).isShutdown()) {
                    depthHandler.getFrame(frame);
                    displayFrame.buildFrameImage(frame);
                    displayFrame.displayFrame();
                }
                displayFrame.dispose();
            }
        }).start();
    }

    protected static void displayVideo(final VideoHandler videoHandler, final KinectManager manager) {
        new Thread(new Runnable() {
            private int[] frame = new int[640 * 480];

            @Override
            public void run() {
                IndependantDisplayFrame displayFrame = new IndependantDisplayFrame("Video Display", 640, 480);
                displayFrame.addKeyListener(CloseKeyListener.getInstance(manager));
                while (!CloseKeyListener.getInstance(manager).isShutdown()) {
                    videoHandler.getFrame(frame);
                    displayFrame.buildFrameImage(frame);
                    displayFrame.displayFrame();
                }
                displayFrame.dispose();
            }
        }).start();
    }
}
