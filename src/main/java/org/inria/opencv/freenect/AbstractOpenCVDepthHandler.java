package org.inria.opencv.freenect;

import org.inria.freenect.DepthHandler;
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
public abstract class AbstractOpenCVDepthHandler implements DepthHandler {
    private final Object lock = new Object();
    private long startTime;

    private int nbFramePerMin;
    private double fps;
    private int nbFrame;
    protected int[] depthFrame;

    protected int width;
    protected int height;
    protected int frameSize;

    public DepthFormat getFormat() {
        return DepthFormat.D11BIT;
    }

    public AbstractOpenCVDepthHandler(int width, int height) {
        startTime = System.nanoTime();
        this.width = width;
        this.height = height;
        frameSize = width * height;
        depthFrame = new int[frameSize];
    }

    @Override
    public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
        synchronized (lock) {
            nbFrame++;

            int ix = 0;
            if (depthFrame != null) {
                for( int i = 0; i < mode.getFrameSize();) {
                    int lo = frame.get(i++) & 255;
                    int hi = frame.get(i++) & 255;
                    int sample = hi << 8 | lo;
                    depthFrame[ix++] = sample;
                }
                manipulateFrame();
            }
        }
        if (nbFramePerMin == 30) {
            fps = ((double) System.nanoTime() - startTime) / 1000000000;
            nbFramePerMin = 0;
            startTime = System.nanoTime();
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
