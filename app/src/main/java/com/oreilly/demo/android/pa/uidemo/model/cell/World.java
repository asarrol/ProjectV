package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;

/**
 * This interface represents a world that can be populated with actors.
 * @see Actor
 */
public interface World {
  /**
   * This method adds an actor to this world at the given position.
   */
  void addActor(Actor actor, int xpos, int ypos);
}