package com.oreilly.demo.android.pa.uidemo.controller;

import android.view.View;

/**
 * Created by Sarah on 5/3/2017.
 */

public class UIUpdateThread implements Runnable {

    private volatile boolean done;
    private View view;

    UIUpdateThread(View view) {
        this.view = view;
    }

    public void done() { done = true; }

    @Override
    public void run() {

        while (!done) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            view.postInvalidate();
        }
    }

}
