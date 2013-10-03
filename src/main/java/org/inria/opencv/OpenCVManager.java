package org.inria.opencv;

import org.opencv.core.Core;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 11:32
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class OpenCVManager {

    public void initializeOpenCV() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

}
