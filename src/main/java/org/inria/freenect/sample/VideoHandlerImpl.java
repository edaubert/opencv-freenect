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
    protected final Object lock;

    protected int[] videoFrame;

    protected int width;
    protected int height;
    protected int frameSize;

    protected VideoFormat format;

    public VideoFormat getFormat() {
        return format;
    }

    public VideoHandlerImpl(int width, int height, VideoFormat format) {
        lock = new Object();
        this.format = format;
        this.width = width;
        this.height = height;
        frameSize = width * height;
        videoFrame = new int[frameSize];
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
            }
        }
        // following line avoid JVM crash due to segfault on freenect!! Without this line the buffer where frames are stored grows in a infinite way
        frame.position(0);
    }

    protected void processIR_8BIT(FrameMode mode, ByteBuffer frame, int timestamp) {
        int num_samples = frameSize;
        int ix = 0;
        for (int i = 0; i < num_samples; ) {
            int b = frame.get(i++);
            videoFrame[ix++] = 0xFF000000 | (b & 255) << 16 | (b & 255) << 8 | (b & 255);
        }
    }

    protected void processIR_10BIT(FrameMode mode, ByteBuffer frame, int timestamp) {
        int num_samples = 2 * frameSize;
        int ix = 0;
        for (int i = 0; i < num_samples; ) {
            int lo = frame.get(i++) & 255;
            int hi = frame.get(i++) & 255;
            int sample = (hi << 8 | lo) >> 2;
            videoFrame[ix++] = 0xFF000000 | (sample & 255) << 16 | (sample & 255) << 8 | (sample & 255);
        }
    }

    protected void processRGB(FrameMode mode, ByteBuffer frame, int timestamp) {
        int num_samples = 3 * frameSize;
        int ix = 0;
        for (int i = 0; i < num_samples; ) {
            int sample = 0xFF000000 | (frame.get(i++) & 255) << 16 | (frame.get(i++) & 255) << 8 | (frame.get(i++) & 255);
            videoFrame[ix++] = sample;
        }
    }

    public void getFrame(int[] dst) {
        synchronized (lock) {
            System.arraycopy(videoFrame, 0, dst, 0, frameSize);
        }
    }
}
