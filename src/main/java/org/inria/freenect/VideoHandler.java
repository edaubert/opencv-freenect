package org.inria.freenect;

import org.openkinect.freenect.VideoFormat;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 10:26
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public interface VideoHandler extends org.openkinect.freenect.VideoHandler, Handler<VideoFormat> {
}
