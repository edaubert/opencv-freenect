package org.inria.freenect.sample;

import org.inria.freenect.DepthHandler;
import org.openkinect.freenect.DepthFormat;
import org.openkinect.freenect.FrameMode;

import java.nio.ByteBuffer;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 09:11
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class DepthHandlerImpl implements DepthHandler {
    protected final Object lock;

    protected int[] depthFrame;

    protected int width;
    protected int height;
    protected int frameSize;

    protected DepthFormat format;

    public DepthFormat getFormat() {
        return format;
    }

    public DepthHandlerImpl(int width, int height, DepthFormat format) {
        lock = new Object();
        this.format = format;
        this.width = width;
        this.height = height;
        frameSize = width * height;
        depthFrame = new int[frameSize];
    }

    @Override
    public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
        synchronized (lock) {
            if (depthFrame != null) {
                // TODO format is not manage here (only D11BIT is handled)
                processD11BIT(mode, frame, timestamp);
            }
        }
        // following line avoid JVM crash due to segfault on freenect!! Without this line the buffer where frames are stored grows in a infinite way
        frame.position(0);
    }

    protected void processD11BIT(FrameMode mode, ByteBuffer frame, int timestamp) {
        int ix = 0;
        for( int i = 0; i < mode.getFrameSize();) {
            int lo = frame.get(i++) & 255;
            int hi = frame.get(i++) & 255;
            int depth = (hi << 8 | lo) & 2047; // 2047 is the number of possible value so we ensure that the value we get is lesser or equals to that
            depthFrame[ix++] = depth2rgb((short)depth);
        }
    }

    private int depth2rgb(short depth) {
        int r, g, b;

        float v = depth / 2047f;
        v = (float) Math.pow(v, 3) * 6;
        v = v * 6 * 256;

        int pval = Math.round(v);
        int lb = pval & 0xff;
        switch (pval >> 8) {
            case 0:
                b = 255;
                g = 255 - lb;
                r = 255 - lb;
                break;
            case 1:
                b = 255;
                g = lb;
                r = 0;
                break;
            case 2:
                b = 255 - lb;
                g = 255;
                r = 0;
                break;
            case 3:
                b = 0;
                g = 255;
                r = lb;
                break;
            case 4:
                b = 0;
                g = 255 - lb;
                r = 255;
                break;
            case 5:
                b = 0;
                g = 0;
                r = 255 - lb;
                break;
            default:
                r = 0;
                g = 0;
                b = 0;
                break;
        }

        return (0xFF) << 24
                | (b & 0xFF) << 16
                | (g & 0xFF) << 8
                | (r & 0xFF);
    }

    public void getFrame(int[] dst) {
        synchronized (lock) {
            System.arraycopy(depthFrame, 0, dst, 0, frameSize);
        }
    }

}
