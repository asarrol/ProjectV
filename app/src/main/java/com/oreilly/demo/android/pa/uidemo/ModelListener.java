package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.monster.MonsterStateTransitions;

/**
 * Created by Sarah on 5/3/2017.
 */

public interface ModelListener extends MonsterStateListener, CellListener {
    void onChangeModel();
}
