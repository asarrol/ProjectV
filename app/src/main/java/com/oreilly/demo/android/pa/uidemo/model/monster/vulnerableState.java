package com.oreilly.demo.android.pa.uidemo.model.monster;

import com.oreilly.demo.android.pa.uidemo.common.Constants;

/**
 * Created by Sarah on 5/2/2017.
 */
//monster's vulnerable state

public class vulnerableState implements MonsterState {

    private MonsterStateTransitions statetransition;

    public vulnerableState(MonsterStateTransitions state){
        this.statetransition = state;
    }

    @Override
    public int getid(){
        return Constants.STATE_VULNERABLE;
    }

}
