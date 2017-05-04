package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.timer.Time;

import java.util.Timer;

/**
 * Created by Sarah on 5/3/2017.
 */

public interface TimerListener {
    void onTick(Time observer);
}
