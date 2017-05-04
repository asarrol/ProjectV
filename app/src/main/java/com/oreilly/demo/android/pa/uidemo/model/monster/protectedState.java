package com.oreilly.demo.android.pa.uidemo.model.monster;

import com.oreilly.demo.android.pa.uidemo.common.Constants;

/**
 * Created by Sarah on 5/2/2017.
 */

public class protectedState implements MonsterState {

    private MonsterStateTransitions monsterstate;

    public protectedState(MonsterStateTransitions state ) {
        this.monsterstate = state;
    }

    @Override
    public int getid(){
        return Constants.STATE_PROTECTED;
    }

}
