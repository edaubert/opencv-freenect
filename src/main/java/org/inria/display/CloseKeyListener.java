package org.inria.display;

import org.inria.freenect.KinectManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 03/10/13
 * Time: 09:31
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class CloseKeyListener implements KeyListener {

    private static CloseKeyListener instance;

    public synchronized static CloseKeyListener getInstance(KinectManager manager) {
        if (instance == null) {
            instance = new CloseKeyListener(manager);
        }
        return instance;
    }

    private KinectManager manager;

    private boolean isShutdown;

    public boolean isShutdown() {
        return isShutdown;
    }

    private CloseKeyListener(KinectManager manager) {
        this.manager = manager;
        isShutdown = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if (!isShutdown) {
            isShutdown = true;
            manager.freeDevice();
            manager.freeContext();
        }
    }
}