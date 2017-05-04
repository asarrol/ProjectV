package com.oreilly.demo.android.pa.uidemo.model.timer;

import com.oreilly.demo.android.pa.uidemo.TimerListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sarah on 5/3/2017.
 */

public class CreateNewMonsters implements Time {

    private Timer timer = new Timer();

    private TimerListener timelistener;

    public void setOnTimerListener(TimerListener listener) {
        this.timelistener = listener;
    }

    //looking to stopwatch for model of constant time
    @Override
    public void start() {
        // The clock model runs onTick every 5000 milliseconds
        timer.schedule(new TimerTask() {
            @Override public void run() {
                // fire event
                timelistener.onTick(CreateNewMonsters.this);
            }
        }, /*initial delay*/ 2000, /*periodic delay*/ 500);
    }

    @Override
    public void stop() {
        timer.cancel();
    }
}
