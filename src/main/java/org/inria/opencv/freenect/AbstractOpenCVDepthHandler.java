package org.inria.opencv.freenect;

import org.inria.freenect.sample.DepthHandlerImpl;
import org.openkinect.freenect.DepthFormat;
import org.openkinect.freenect.FrameMode;

import java.nio.ByteBuffer;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 10:54
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public abstract class AbstractOpenCVDepthHandler extends DepthHandlerImpl {

    public AbstractOpenCVDepthHandler(int width, int height, DepthFormat format) {
        super(width, height, format);
    }

    @Override
    public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
        synchronized (lock) {
            if (depthFrame != null) {
                // TODO format is not manage here (only D11BIT is handled)
                processD11BIT(mode, frame, timestamp);
                manipulateFrame();
            }
        }
        // following line avoid JVM crash due to segfault on freenect!! Without this line the buffer where frames are stored grows in a infinite way
        frame.position(0);
    }

    public void getFrame(int[] dst) {
        synchronized (lock) {
            System.arraycopy(depthFrame, 0, dst, 0, frameSize);
        }
    }

    protected abstract void manipulateFrame();
}
