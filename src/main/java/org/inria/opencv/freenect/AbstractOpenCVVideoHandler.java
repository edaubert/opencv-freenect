package org.inria.opencv.freenect;

import org.inria.freenect.sample.VideoHandlerImpl;
import org.openkinect.freenect.FrameMode;
import org.openkinect.freenect.VideoFormat;

import java.nio.ByteBuffer;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 10:54
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public abstract class AbstractOpenCVVideoHandler extends VideoHandlerImpl {

    public AbstractOpenCVVideoHandler(int width, int height, VideoFormat format) {
        super(width, height, format);
    }

    @Override
    public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
        synchronized (lock) {
            if (videoFrame != null) {
                if (VideoFormat.fromInt(mode.format) == VideoFormat.IR_8BIT) {
                    processIR_8BIT(mode, frame, timestamp);
                } else if (VideoFormat.fromInt(mode.format) == VideoFormat.IR_10BIT) {
                    processIR_10BIT(mode, frame, timestamp);
                } else if (VideoFormat.fromInt(mode.format) == VideoFormat.RGB) {
                    processRGB(mode, frame, timestamp);
                }
                manipulateFrame();
            }
        }
        // following line avoid JVM crash due to segfault on freenect!! Without this line the buffer where frames are stored grows in a infinite way
        frame.position(0);
    }

    public void getFrame(int[] dst) {
        synchronized (lock) {
            System.arraycopy(videoFrame, 0, dst, 0, frameSize);
        }
    }

    protected abstract void manipulateFrame();
}
