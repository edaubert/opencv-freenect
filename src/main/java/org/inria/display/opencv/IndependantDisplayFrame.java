package org.inria.display.opencv;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 03/10/13
 * Time: 09:21
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class IndependantDisplayFrame extends org.inria.display.freenect.IndependantDisplayFrame {

    public IndependantDisplayFrame(String frameTitle, int width, int height) {
        super(frameTitle, width, height);
    }

    public void buildFrameImage(Mat matrice) {
        image = matToBufferedImage(matrice);
    }

    public BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;

        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;

            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;

                // bgr to rgb
                byte b;
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = b;
                }
                break;

            default:
                return null;
        }

        BufferedImage image = new BufferedImage(cols, rows, type);
        image.getRaster().setDataElements(0, 0, cols, rows, data);

        return image;
    }
}
