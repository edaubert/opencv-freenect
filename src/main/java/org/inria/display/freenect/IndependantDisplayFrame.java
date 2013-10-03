package org.inria.display.freenect;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 03/10/13
 * Time: 09:14
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class IndependantDisplayFrame extends JFrame {

//    private JFrame frame;
    protected int width;
    protected int height;
    protected BufferStrategy bufferStrategy;
    protected BufferedImage image;

    public IndependantDisplayFrame(String frameTitle, int width, int height) {
        super(frameTitle);
        setVisible(true);
        setSize(width, height);
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
        image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleImage(width, height);
        this.width = width;
        this.height = height;
    }

    public void buildFrameImage(int[] frame) {
        image.setRGB(0, 0, width, height, frame, 0, width);
    }

    public void displayFrame() {
        try {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
            g.dispose();
            bufferStrategy.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
