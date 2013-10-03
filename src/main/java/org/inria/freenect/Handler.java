package org.inria.freenect;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 02/10/13
 * Time: 10:24
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public interface Handler<T> {

    T getFormat();

    void getFrame(int[] dst);
}
