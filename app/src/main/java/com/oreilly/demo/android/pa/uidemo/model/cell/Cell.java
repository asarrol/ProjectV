package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.CellListener;
import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;

import java.util.List;

/**
 * This interface represents a cell in a world consisting of cells.
 *
 * @see Actor
 * @see World
 */
public interface Cell extends CellListener {

  /**
   * This method adds another cell as a neighbor to this cell.  The
   * other cell does not automatically become a neighbor of this cell.
   */
  void addNeighbor(Cell cell);

  /**
   * This method returns the list of cells that are neighbors of this cell.
   */
  List<Cell> getNeighbors();

  /**
   * This method returns the list of actors currently occupying this cell.
   */
  List<Actor> getOccupants();

  /**
   * This method allows an actor to enter this cell.
   */
  void enter(Actor actor) throws InterruptedException;

  /**
   * This method allows an actor to leave this cell.
   */
  void leave(Actor actor);

  /**
   * This method returns a random neighbor of this cell.
   */
  Cell randomNeighbor();

}