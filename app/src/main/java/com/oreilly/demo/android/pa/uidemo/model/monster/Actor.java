package com.oreilly.demo.android.pa.uidemo.model.monster;

import com.oreilly.demo.android.pa.uidemo.CellListener;
import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.World;

/**
 * This interface represents an actor in a world of cells.
 *
 * @see Cell
 * @see World
 */
public interface Actor extends CellListener {

  /**
   * This method sets the cell that this actor currently occupies.
   * This method is meant to be called by the <code>leave</code> method
   * in the <code>Cell</code> class.
   */
  void setCell(Cell cell);

  /**
   * This method returns the cell that this actor currently occupies.
   */
  Cell getCell();

  /**
   * This method brings this actor to life.
   */
  void start();

  /**
   * This method kills this actor.
   */
  void kill();
}