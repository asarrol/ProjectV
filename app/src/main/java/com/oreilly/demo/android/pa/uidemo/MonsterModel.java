package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.cell.CellWorld;
import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;

import java.util.List;

/**
 * Created by Sarah on 5/3/2017.
 */

public interface MonsterModel extends TimerListener, MonsterStateListener {
    void setMonsterStateListener(MonsterStateListener listener);
    void setModelListener(ModelListener listener);
    CellWorld getWorld();
    void addActor(Actor actor, int xpos, int ypos);
    List<Actor> getLiveActors();
    void addLiveActor(Actor actor);
    void removeLiveActor(Actor actor);
}
