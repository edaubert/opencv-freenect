package org.inria.freenect;

import org.openkinect.freenect.DepthFormat;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 10:26
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public interface DepthHandler extends org.openkinect.freenect.DepthHandler, Handler<DepthFormat> {
}
