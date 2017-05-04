package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;

import java.util.EventObject;

/**
 * This class represents events occuring in cells.  The cell in question
 * is usually the source of the event.
 * @see Cell
 * @see Actor
 */
public class CellEvent extends EventObject {

  private static final long serialVersionUID = 6621950138682436187L;

  /**
   * The actor involved in this event.
   */
  private Actor actor;

  /**
   * This constructor builds a cell event involving the specified actor.
   */
  public CellEvent(Object source, Actor actor) {
    super(source);
    this.actor = actor;
  }

  /**
   * This method returns the actor involved in this event.
   */
  public Actor getActor() { return actor; }

  public String toString() {
    return getClass().getName() + "[" + source + "," + actor + "]";
  }
}