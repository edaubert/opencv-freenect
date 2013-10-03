package org.inria.freenect.sample;

import org.inria.freenect.VideoHandler;
import org.openkinect.freenect.FrameMode;
import org.openkinect.freenect.VideoFormat;

import java.nio.ByteBuffer;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 09:11
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class VideoHandlerImpl implements VideoHandler {
    private final Object lock;
    private long startTime;

    private int nbFramePerMin;
    private double fps;
    private int nbFrame;
    private int[] videoFrame;

    private int width;
    private int height;
    private int frameSize;


    public VideoFormat getFormat() {
        return VideoFormat.RGB;
    }

    public VideoHandlerImpl(int width, int height) {
        lock = new Object();
        startTime = System.nanoTime();
        this.width = width;
        this.height = height;
        frameSize = width * height;
        videoFrame = new int[frameSize];
    }

    @Override
    public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
        synchronized (lock) {
            nbFrame++;
            int num_samples;
            int ix = 0;
            if (videoFrame != null) {
                if (VideoFormat.fromInt(mode.format) == VideoFormat.IR_8BIT) {
                    num_samples = frameSize;
                    for (int i = 0; i < num_samples; ) {

                        //int lo = frame.get(i++);
                        //int a = 0xFF000000 | lo << 16 | lo << 8 | lo;
                        int sample = frame.get(i++);
                        videoFrame[ix++] = sample;
                    }
                } else if (VideoFormat.fromInt(mode.format) == VideoFormat.IR_10BIT) {
                    num_samples = 2 * frameSize;
                    for (int i = 0; i < num_samples; ) {
                        int lo = frame.get(i++) & 255;
                        int hi = frame.get(i++) & 255;
                        //pixVideo[ix++] = hi << 8 | lo;
                        int sample = (hi << 8 | lo) >> 2;
                        //pixVideo[ix++] = 0xFF000000 | a << 16 | a << 8 | a;
                        videoFrame[ix++] = sample;
                    }
                } else if (VideoFormat.fromInt(mode.format) == VideoFormat.RGB) {
                    num_samples = 3 * frameSize;
                    for (int i = 0; i < num_samples; ) {
                        int sample = 0xFF000000 | (frame.get(i++) & 255) << 16 | (frame.get(i++) & 255) << 8 | (frame.get(i++) & 255);
                        videoFrame[ix++] = sample;
                    }
                }

            }
        }
        if (nbFrame == 30) {
            fps = ((double) System.nanoTime() - startTime) / 1000000000;
            nbFrame = 0;
            startTime = System.nanoTime();
        }
        // following line avoid JVM crash due to segfault on freenect!! Without this line the buffer where frames are stored grows in a infinite way
        frame.position(0);
    }

    public void getFrame(int[] dst) {
        synchronized (lock) {
            System.arraycopy(videoFrame, 0, dst, 0, frameSize);
        }
    }
}
