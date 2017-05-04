package com.oreilly.demo.android.pa.uidemo.model.monster;

/**
 * Created by Sarah on 5/2/2017.
 */

public interface MonsterStateTransitions {
    //state transitions
    void toVulnerableState();
    void toProtectedState();

    //actions
    void init();

}
